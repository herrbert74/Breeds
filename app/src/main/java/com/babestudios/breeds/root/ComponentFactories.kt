package com.babestudios.breeds.root

import com.babestudios.breeds.domain.api.BreedsRepository
import com.babestudios.breeds.ui.breedimages.BreedImagesComponent
import com.babestudios.breeds.ui.breedimages.BreedImagesExecutor
import com.babestudios.breeds.ui.breeds.BreedsExecutor
import com.babestudios.breeds.ui.breeds.BreedsComponent
import kotlinx.coroutines.CoroutineDispatcher

/**
 * These are higher order functions with common parameters used in the RootComponent,
 * that return functions, that create the Decompose feature components from feature specific parameters.
 */

internal fun createBreedsFactory(
	breedsRepository: BreedsRepository,
	mainContext: CoroutineDispatcher,
	ioContext: CoroutineDispatcher,
): CreateBreedsComp = { childContext, finishHandler, output ->
	BreedsComponent(
		componentContext = childContext,
		breedsExecutor = BreedsExecutor(breedsRepository, mainContext, ioContext),
		output = output,
		finishHandler = finishHandler,
	)
}

internal fun createBreedImagesFactory(
	breedsRepository: BreedsRepository,
	mainContext: CoroutineDispatcher,
	ioContext: CoroutineDispatcher,
): CreateBreedImagesComp = { childContext, selectedBreed, selectedSubBreed, output ->
	BreedImagesComponent(
		componentContext = childContext,
		breedImagesExecutor = BreedImagesExecutor(breedsRepository, mainContext, ioContext),
		selectedBreed = selectedBreed,
		selectedSubBreed = selectedSubBreed,
		output = output,
	)
}
