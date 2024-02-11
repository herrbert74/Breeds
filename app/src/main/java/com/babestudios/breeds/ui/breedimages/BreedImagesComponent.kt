package com.babestudios.breeds.ui.breedimages

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.babestudios.breeds.ext.asValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

interface BreedImagesComp {

	fun onBackClicked()

	sealed class Output {
		data object Back : Output()
	}

	val state: Value<BreedImagesStore.State>

	val selectedBreed: String

	val selectedSubBreed: String?

}

class BreedImagesComponent(
	componentContext: ComponentContext,
	private val breedImagesExecutor: BreedImagesExecutor,
	override val selectedBreed: String,
	override val selectedSubBreed: String?,
	private val output: FlowCollector<BreedImagesComp.Output>,
) : BreedImagesComp, ComponentContext by componentContext {

	private var breedImagesStore: BreedImagesStore =
		BreedImagesStoreFactory(
			LoggingStoreFactory(DefaultStoreFactory()),
			breedImagesExecutor
		).create(selectedBreed, selectedSubBreed)

	override fun onBackClicked() {
		CoroutineScope(breedImagesExecutor.mainContext).launch {
			output.emit(BreedImagesComp.Output.Back)
		}
	}

	override val state: Value<BreedImagesStore.State>
		get() = breedImagesStore.asValue()

}
