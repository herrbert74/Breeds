package com.babestudios.breeds.testhelper

import com.babestudios.breeds.domain.model.Breed
import com.github.michaelbull.result.Ok

/**
 * This is an example of an ObjectMother that can be used in both Unit and Android UI tests.
 * As such it would go into its own module in a normal project.
 */
object BreedsMother {

	fun createBreedsList() = Ok(
		listOf(
			Breed(breed = "affenpischer", subBreeds = emptyList()),
			Breed(breed = "african", subBreeds = emptyList()),
			Breed(breed = "airedale", subBreeds = emptyList()),
			Breed(breed = "akita", subBreeds = emptyList()),
			Breed(breed = "appenzeller", subBreeds = emptyList()),
			Breed(breed = "australian", subBreeds = listOf("kelpie", "shepherd")),
			Breed(breed = "bakharwal", subBreeds = emptyList()),
			Breed(breed = "basenji", subBreeds = emptyList()),
			Breed(breed = "beagle", subBreeds = emptyList()),
			Breed(breed = "bluetick", subBreeds = emptyList()),
		)
	)

}
