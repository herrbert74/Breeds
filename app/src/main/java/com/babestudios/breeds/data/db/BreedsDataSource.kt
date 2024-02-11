package com.babestudios.breeds.data.db

import com.babestudios.breeds.domain.model.Breed
import kotlinx.coroutines.flow.Flow

interface BreedsDataSource {

	suspend fun purgeDatabase()

	suspend fun insertBreeds(breeds: List<Breed>)

	fun getBreeds(): List<Breed>

}
