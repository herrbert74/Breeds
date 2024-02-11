package com.babestudios.breeds.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.babestudios.breeds.domain.api.BreedsRepository
import com.babestudios.breeds.di.IoDispatcher
import com.babestudios.breeds.di.MainDispatcher
import com.babestudios.breeds.ui.breedimages.BreedImagesComp
import com.babestudios.breeds.ui.breeds.BreedsComp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.FlowCollector

typealias CreateBreedsComp = (ComponentContext, () -> Unit, FlowCollector<BreedsComp.Output>) -> BreedsComp
typealias CreateBreedImagesComp
	= (ComponentContext, String, String?, FlowCollector<BreedImagesComp.Output>) -> BreedImagesComp

interface BreedsRootComp {
	val childStackValue: Value<ChildStack<Configuration, BreedsChild>>
}

class BreedsRootComponent internal constructor(
	componentContext: ComponentContext,
	private val finishHandler: () -> Unit,
	private val createBreedsComp: CreateBreedsComp,
	private val createBreedImagesComp: CreateBreedImagesComp,
) : BreedsRootComp,
	ComponentContext by componentContext {

	constructor(
		componentContext: ComponentContext,
		@MainDispatcher mainContext: CoroutineDispatcher,
		@IoDispatcher ioContext: CoroutineDispatcher,
		breedsRepository: BreedsRepository,
		finishHandler: () -> Unit,
	) : this(
		componentContext = componentContext,
		finishHandler,
		createBreedsComp = createBreedsFactory(breedsRepository, mainContext, ioContext),
		createBreedImagesComp = createBreedImagesFactory(breedsRepository, mainContext, ioContext),
	)

	private val stack = childStack(
		source = navigation,
		initialStack = { listOf(Configuration.Breeds) },
		handleBackButton = true,
		childFactory = ::createChild
	)

	override val childStackValue = stack

	private fun createChild(configuration: Configuration, componentContext: ComponentContext): BreedsChild {
		return when (configuration) {

			is Configuration.Breeds -> BreedsChild.Breeds(
				createBreedsComp(
					componentContext.childContext(key = "BreedsComponent"),
					finishHandler,
					FlowCollector(::onBreedsOutput)
				)
			)

			is Configuration.BreedImages -> BreedsChild.BreedImages(
				createBreedImagesComp(
					componentContext.childContext(key = "BreedImagesComponent"),
					configuration.selectedBreed,
					configuration.selectedSubBreed,
					FlowCollector(::onBreedImagesOutput)
				)
			)
		}
	}

}
