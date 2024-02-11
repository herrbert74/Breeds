package com.babestudios.breeds.domain.api

import com.babestudios.breeds.domain.model.Breed
import com.babestudios.breeds.ext.ApiResult

interface BreedsRepository {

	suspend fun getAllBreeds(): ApiResult<List<Breed>>

	suspend fun getRandomImage(): String

	suspend fun getBreedImages(breed: String): ApiResult<List<String>>

	suspend fun getSubBreedImages(breed: String, subBreed: String): ApiResult<List<String>>

}