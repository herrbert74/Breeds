package com.babestudios.breeds.root

import com.babestudios.breeds.ui.breedimages.BreedImagesComp
import com.babestudios.breeds.ui.breeds.BreedsComp

sealed class BreedsChild {

	data class Breeds(val component: BreedsComp) : BreedsChild()
	data class BreedImages(val component: BreedImagesComp) : BreedsChild()

}
