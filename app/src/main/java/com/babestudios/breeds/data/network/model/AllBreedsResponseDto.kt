package com.babestudios.breeds.data.network.model

import com.babestudios.breeds.domain.model.Breed

data class AllBreedsResponseDto(val message: Map<String, List<String>>, val status: String)

fun AllBreedsResponseDto.toBreeds(): List<Breed> {
	return this.message.map { Breed(it.key, it.value) }
}
