package com.babestudios.breeds.data

import com.babestudios.breeds.data.db.BreedsDataSource
import com.babestudios.breeds.data.network.BreedsService
import com.babestudios.breeds.data.network.model.toBreeds
import com.babestudios.breeds.domain.api.BreedsRepository
import com.babestudios.breeds.domain.model.Breed
import com.babestudios.breeds.ext.ApiResult
import com.babestudios.breeds.ext.apiRunCatching
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.map
import com.github.michaelbull.result.onSuccess
import com.github.michaelbull.result.orElse
import javax.inject.Singleton

const val RANDOM_IMAGE_COUNT = "10"

@Singleton
class BreedsAccessor(
	private val breedsService: BreedsService,
	private val breedsDataSource: BreedsDataSource,
) : BreedsRepository {

	override suspend fun getAllBreeds(): ApiResult<List<Breed>> {
		return apiRunCatching { breedsService.getBreeds() }
			.map { breeds -> breeds.toBreeds() }
			.onSuccess { breeds -> breedsDataSource.insertBreeds(breeds = breeds) }
			.orElse { throwable ->
				val breedsFromDb = breedsDataSource.getBreeds()
				if (breedsFromDb.isNotEmpty()) Ok(breedsFromDb)
				else Err(throwable)
			}
	}

	override suspend fun getRandomImage(): String {
		return breedsService.getRandomImage().message
	}

	override suspend fun getBreedImages(breed: String): ApiResult<List<String>> {
		return apiRunCatching { breedsService.getImagesForBreed(breed, RANDOM_IMAGE_COUNT).message }
	}

	override suspend fun getSubBreedImages(breed: String, subBreed: String): ApiResult<List<String>> {
		val breeds = breedsService.getImagesForSubBreed(breed, subBreed, RANDOM_IMAGE_COUNT)
		return apiRunCatching { breeds.message }
	}

}
