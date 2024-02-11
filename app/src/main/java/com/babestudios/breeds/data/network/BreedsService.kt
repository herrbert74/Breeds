package com.babestudios.breeds.data.network

import com.babestudios.breeds.data.network.model.AllBreedsResponseDto
import com.babestudios.breeds.data.network.model.BreedImageResponseDto
import com.babestudios.breeds.data.network.model.BreedImagesResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface BreedsService {

	@GET("api/breeds/list/all")
	suspend fun getBreeds(): AllBreedsResponseDto

	@GET("api/breeds/image/random")
	suspend fun getRandomImage(): BreedImageResponseDto

	@GET("api/breed/{breed}/images/random/{count}")
	suspend fun getImagesForBreed(
		@Path("breed") breed: String,
		@Path("count") count: String
	): BreedImagesResponseDto

	@GET("api/breed/{breed}/{sub-breed}/images/random/{count}")
	suspend fun getImagesForSubBreed(
		@Path("breed") breed: String,
		@Path("sub-breed") subBreed: String,
		@Path("count") count: String
	): BreedImagesResponseDto

}
