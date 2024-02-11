package com.babestudios.breeds.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.babestudios.breeds.design.BreedsTheme
import com.babestudios.breeds.ui.breedimages.BreedImagesScreen
import com.babestudios.breeds.ui.breeds.BreedsScreen

@Composable
internal fun BreedsRootContent(component: BreedsRootComp) {

	val stack = component.childStackValue

	BreedsTheme {
		Children(stack = stack, animation = stackAnimation(scale())) {
			when (val child = it.instance) {
				is BreedsChild.Breeds -> BreedsScreen(child.component)
				is BreedsChild.BreedImages -> BreedImagesScreen(child.component)
			}
		}
	}

}
