package com.babestudios.breeds.ui.breedimages

import com.arkivanov.mvikotlin.core.store.Store
import com.babestudios.breeds.ui.breedimages.BreedImagesStore.Intent
import com.babestudios.breeds.ui.breedimages.BreedImagesStore.State

interface BreedImagesStore : Store<Intent, State, Nothing> {

	sealed class Intent

	data class State(
		val breed: String = "",
		val subBreed: String? = null,
		val breedImages: List<String> = emptyList(),
		val error: Throwable? = null
	)

}