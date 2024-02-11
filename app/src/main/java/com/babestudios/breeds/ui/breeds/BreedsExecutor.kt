package com.babestudios.breeds.ui.breeds

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.babestudios.breeds.domain.api.BreedsRepository
import com.babestudios.breeds.domain.model.Breed
import com.babestudios.breeds.ui.breeds.BreedsStore.Intent
import com.babestudios.breeds.ui.breeds.BreedsStore.State
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BreedsExecutor(
	private val breedsRepository: BreedsRepository,
	val mainContext: CoroutineDispatcher,
	private val ioContext: CoroutineDispatcher,
) : CoroutineExecutor<Intent, BootstrapIntent, State, Message, Nothing>(mainContext) {

	override fun executeAction(action: BootstrapIntent, getState: () -> State) {
		when (action) {
			is BootstrapIntent.ShowDogBreeds -> {
				loadBreeds(getState)
			}
		}
	}

	private fun loadBreeds(getState: () -> State) {
		if (getState().breeds.isNotEmpty()) {
			dispatch(Message.ShowDogBreeds(getState().breeds))
		} else {
			scope.launch(ioContext) {
				breedsRepository.getAllBreeds()
					.onSuccess {
						withContext(mainContext) { dispatch(Message.ShowDogBreeds(it)) }
					}.onFailure {
						withContext(mainContext) { dispatch(Message.ShowError(it)) }
					}
			}
		}
	}

	override fun executeIntent(intent: Intent, getState: () -> State) {
		when (intent) {
			Intent.SortBreedsClicked -> sortBreeds(getState().breeds)
			Intent.RetryClicked -> loadBreeds(getState)
		}
	}

	private fun sortBreeds(breeds: List<Breed>) {
		dispatch(Message.ShowDogBreeds(breeds.reversed()))
	}

}