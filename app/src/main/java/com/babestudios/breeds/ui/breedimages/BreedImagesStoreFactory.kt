package com.babestudios.breeds.ui.breedimages

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.babestudios.breeds.ui.breedimages.BreedImagesStore.Intent
import com.babestudios.breeds.ui.breedimages.BreedImagesStore.State

class BreedImagesStoreFactory(
	private val storeFactory: StoreFactory,
	private val breedImagesExecutor: BreedImagesExecutor,
) {

	fun create(selectedBreed: String, selectedSubBreed: String?): BreedImagesStore =
		object : BreedImagesStore, Store<Intent, State, Nothing> by storeFactory.create(
			name = "BreedImagesStore",
			initialState = State(breed = selectedBreed, subBreed = selectedSubBreed),
			bootstrapper = BreedImagesBootstrapper(),
			executorFactory = { breedImagesExecutor },
			reducer = BreedImagesReducer
		) {}

	private class BreedImagesBootstrapper : CoroutineBootstrapper<BootstrapIntent>() {
		override fun invoke() {
			dispatch(BootstrapIntent.ShowBreedImages)
		}
	}

	private object BreedImagesReducer : Reducer<State, Message> {
		override fun State.reduce(msg: Message): State =
			when (msg) {
				is Message.ShowBreedImages -> copy(breedImages = msg.breedImages)
				is Message.ShowError -> copy(error = msg.throwable)
			}
	}

}

sealed class Message {
	data class ShowBreedImages(val breedImages: List<String>) : Message()
	data class ShowError(val throwable: Throwable) : Message()
}

sealed class BootstrapIntent {
	data object ShowBreedImages : BootstrapIntent()
}
