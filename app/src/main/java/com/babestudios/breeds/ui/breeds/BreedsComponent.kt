package com.babestudios.breeds.ui.breeds

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.babestudios.breeds.ext.asValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import timber.log.Timber

interface BreedsComp {

	fun onItemClicked(breed: String, subBreed: String?)

	fun onSortBreedsClicked()

	fun onRetryClicked()

	fun onBackClicked()

	val state: Value<BreedsStore.State>

	sealed class Output {
		data class Selected(val breed: String, val subBreed: String?) : Output()
	}

}

class BreedsComponent(
	componentContext: ComponentContext,
	val breedsExecutor: BreedsExecutor,
	private val output: FlowCollector<BreedsComp.Output>,
	private val finishHandler: () -> Unit,
) : BreedsComp, ComponentContext by componentContext {

	private var breedsStore: BreedsStore =
		BreedsStoreFactory(LoggingStoreFactory(DefaultStoreFactory()), breedsExecutor).create(stateKeeper)

	override fun onItemClicked(breed: String, subBreed: String?) {
		CoroutineScope(breedsExecutor.mainContext).launch {
			output.emit(BreedsComp.Output.Selected(breed = breed, subBreed = subBreed))
		}
	}

	override fun onSortBreedsClicked() {
		breedsStore.accept(BreedsStore.Intent.SortBreedsClicked)
	}

	override fun onRetryClicked() {
		Timber.d("zsoltbertalan* onRetryClicked: ")
		breedsStore.accept(BreedsStore.Intent.RetryClicked)
	}

	override fun onBackClicked() {
		CoroutineScope(breedsExecutor.mainContext).launch {
			finishHandler.invoke()
			breedsStore.dispose()
		}
	}

	override val state: Value<BreedsStore.State>
		get() = breedsStore.asValue()

}
