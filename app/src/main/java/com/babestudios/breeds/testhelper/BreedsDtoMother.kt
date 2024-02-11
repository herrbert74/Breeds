package com.babestudios.breeds.testhelper

import com.babestudios.breeds.data.network.model.AllBreedsResponseDto

/**
 * This is an example of an ObjectMother that can be used in both Unit and Android UI tests.
 * As such it would go into its own module in a normal project.
 */
object BreedsDtoMother {

	fun createBreedsResponse() = AllBreedsResponseDto(
		message = mapOf(
			"affenpischer" to emptyList(),
			"african" to emptyList(),
			"airedale" to emptyList(),
			"akita" to emptyList(),
			"appenzeller" to emptyList(),
			"australian" to listOf("kelpie", "shepherd"),
			"bakharwal" to emptyList(),
			"basenji" to emptyList(),
			"beagle" to emptyList(),
			"bluetick" to emptyList(),
		),
		status = "success"
	)

}
