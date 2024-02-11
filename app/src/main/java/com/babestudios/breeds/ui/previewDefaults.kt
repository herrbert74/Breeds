package com.babestudios.breeds.ui

import com.babestudios.breeds.data.BreedsAccessor
import com.babestudios.breeds.data.db.BreedsDataSource
import com.babestudios.breeds.data.network.BreedsService
import com.babestudios.breeds.data.network.model.AllBreedsResponseDto
import com.babestudios.breeds.data.network.model.BreedImageResponseDto
import com.babestudios.breeds.data.network.model.BreedImagesResponseDto
import com.babestudios.breeds.domain.model.Breed
import com.babestudios.breeds.ui.breeds.BreedsExecutor
import kotlinx.coroutines.Dispatchers

val defaultBreedsService = object : BreedsService {
	override suspend fun getBreeds(): AllBreedsResponseDto {
		TODO("Not yet implemented")
	}

	override suspend fun getRandomImage(): BreedImageResponseDto {
		TODO("Not yet implemented")
	}

	override suspend fun getImagesForBreed(breed: String, count: String): BreedImagesResponseDto {
		TODO("Not yet implemented")
	}

	override suspend fun getImagesForSubBreed(
		breed: String,
		subBreed: String,
		count: String
	): BreedImagesResponseDto {
		TODO("Not yet implemented")
	}
}

val defaultDataSource = object : BreedsDataSource {
	override suspend fun purgeDatabase() {
		TODO("Not yet implemented")
	}

	override suspend fun insertBreeds(breeds: List<Breed>) {
		TODO("Not yet implemented")
	}

	override fun getBreeds(): List<Breed> {
		TODO("Not yet implemented")
	}

}

fun defaultBreedsExecutor() = BreedsExecutor(BreedsAccessor(defaultBreedsService, defaultDataSource), Dispatchers.Main, Dispatchers
	.IO)