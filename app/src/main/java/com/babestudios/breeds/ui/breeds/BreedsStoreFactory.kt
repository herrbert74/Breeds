package com.babestudios.breeds.ui.breeds

import com.arkivanov.essenty.statekeeper.StateKeeper
import com.arkivanov.essenty.statekeeper.consume
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.babestudios.breeds.ui.breeds.BreedsStore.Intent
import com.babestudios.breeds.ui.breeds.BreedsStore.State
import com.babestudios.breeds.domain.model.Breed

class BreedsStoreFactory(
	private val storeFactory: StoreFactory,
	private val breedsExecutor: BreedsExecutor,
) {

	fun create(stateKeeper: StateKeeper): BreedsStore =
		object : BreedsStore, Store<Intent, State, Nothing> by storeFactory.create(
			name = "BreedsStore",
			initialState = stateKeeper.consume(key = "BreedsStore") ?: State(),
			bootstrapper = BreedsBootstrapper(),
			executorFactory = { breedsExecutor },
			reducer = BreedsReducer
		) {
		}.also {
			stateKeeper.register(key = "BreedsStore") {
				it.state.copy()
			}
		}

	private class BreedsBootstrapper : CoroutineBootstrapper<BootstrapIntent>() {
		override fun invoke() {
			dispatch(BootstrapIntent.ShowDogBreeds)
		}
	}

	private object BreedsReducer : Reducer<State, Message> {
		override fun State.reduce(msg: Message): State =
			when (msg) {
				is Message.ShowDogBreeds -> copy(breeds = msg.breeds, error = null)
				is Message.ShowError -> copy(error = msg.throwable)
			}
	}

}

sealed class Message {
	data class ShowDogBreeds(val breeds: List<Breed>) : Message()
	data class ShowError(val throwable: Throwable) : Message()
}

sealed class BootstrapIntent {
	data object ShowDogBreeds : BootstrapIntent()
}
