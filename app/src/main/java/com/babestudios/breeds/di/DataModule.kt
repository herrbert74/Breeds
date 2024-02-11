package com.babestudios.breeds.di

import com.babestudios.breeds.DOGS_BASE_URL
import com.babestudios.breeds.data.BreedsAccessor
import com.babestudios.breeds.data.db.BreedDbo
import com.babestudios.breeds.data.db.BreedsDao
import com.babestudios.breeds.data.db.BreedsDataSource
import com.babestudios.breeds.domain.api.BreedsRepository
import com.babestudios.breeds.data.network.BreedsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@Suppress("unused")
@InstallIn(SingletonComponent::class)
class DataModule {

	@Provides
	@Singleton
	internal fun provideBreedsRetrofit(): Retrofit {
		val logging = HttpLoggingInterceptor()
		logging.level = HttpLoggingInterceptor.Level.BODY

		val httpClient = OkHttpClient.Builder()

		httpClient.addInterceptor(logging)
		return Retrofit.Builder()
			.baseUrl(DOGS_BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.client(httpClient.build())
			.build()
	}

	@Provides
	@Singleton
	fun provideRealmConfiguration() = RealmConfiguration.Builder(
		schema = setOf(BreedDbo::class)
	).build()

	@Provides
	@Singleton
	fun provideRealm(realmConfiguration: RealmConfiguration) = Realm.open(realmConfiguration)

	@Provides
	@Singleton
	internal fun providePhotosDataSource(
		realm: Realm
	): BreedsDataSource {
		return BreedsDao(realm)
	}

	@Provides
	@Singleton
	internal fun provideBreedsService(retroFit: Retrofit): BreedsService =
		retroFit.create(BreedsService::class.java)

	@Provides
	@Singleton
	fun provideBreedsRepository(breedsService: BreedsService, breedsDataSource: BreedsDataSource): BreedsRepository =
		BreedsAccessor(breedsService, breedsDataSource)

	@DefaultDispatcher
	@Provides
	fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

	@IoDispatcher
	@Provides
	fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

	@MainDispatcher
	@Provides
	fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher
