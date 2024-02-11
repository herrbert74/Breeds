package com.babestudios.breeds.ui.breedimages

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.babestudios.breeds.domain.api.BreedsRepository
import com.babestudios.breeds.ui.breedimages.BreedImagesStore.Intent
import com.babestudios.breeds.ui.breedimages.BreedImagesStore.State
import com.github.michaelbull.result.get
import com.github.michaelbull.result.getOrElse
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.github.michaelbull.result.runCatching
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BreedImagesExecutor constructor(
	private val breedsRepository: BreedsRepository,
	val mainContext: CoroutineDispatcher,
	private val ioContext: CoroutineDispatcher,
) : CoroutineExecutor<Intent, BootstrapIntent, State, Message, Nothing>(mainContext) {

	override fun executeAction(action: BootstrapIntent, getState: () -> State) {
		when (action) {
			is BootstrapIntent.ShowBreedImages -> {
				scope.launch(ioContext) {
					val breed = getState().breed
					val subBreed = getState().subBreed
					runCatching {
						if (subBreed == null) breedsRepository.getBreedImages(breed)
						else breedsRepository.getSubBreedImages(breed, subBreed)
					}
						.onSuccess { withContext(mainContext) { dispatch(Message.ShowBreedImages(it.getOrElse { emptyList() }))
						} }
						.onFailure { withContext(mainContext) { dispatch(Message.ShowError(it)) } }
				}
			}
		}
	}

}
