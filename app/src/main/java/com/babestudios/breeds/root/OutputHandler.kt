package com.babestudios.breeds.root

import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.babestudios.breeds.ui.breedimages.BreedImagesComp
import com.babestudios.breeds.ui.breeds.BreedsComp

val navigation = StackNavigation<Configuration>()

internal fun onBreedsOutput(output: BreedsComp.Output) = when (output) {

	is BreedsComp.Output.Selected ->
		navigation.push(Configuration.BreedImages(selectedBreed = output.breed, selectedSubBreed = output.subBreed))

}

internal fun onBreedImagesOutput(output: BreedImagesComp.Output) = when (output) {
	BreedImagesComp.Output.Back -> navigation.pop()
}
