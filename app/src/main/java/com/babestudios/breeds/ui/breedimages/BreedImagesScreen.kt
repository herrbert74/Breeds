package com.babestudios.breeds.ui.breedimages

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.babestudios.breeds.R
import com.babestudios.breeds.design.BreedsTheme
import com.babestudios.breeds.design.smallDimensions

@Composable
fun BreedImagesScreen(component: BreedImagesComp) {

	val model by component.state.subscribeAsState()

	BackHandler(onBack = { component.onBackClicked() })

	Scaffold(
		topBar = {
			TopAppBar(
				colors = TopAppBarDefaults.topAppBarColors(
					containerColor = BreedsTheme.colorScheme.primaryContainer,
					titleContentColor = BreedsTheme.colorScheme.primary,
				),
				title = {
					Text(model.subBreed ?: model.breed)
				},
				navigationIcon = {
					IconButton(onClick = { component.onBackClicked() }) {
						Icon(
							imageVector = Icons.AutoMirrored.Filled.ArrowBack,
							contentDescription = "Finish",
							tint = MaterialTheme.colorScheme.onPrimaryContainer
						)
					}
				}
			)
		}
	) { innerPadding ->
		LazyColumn(
			modifier = Modifier.padding(innerPadding)
		) {
			items(
				model.breedImages.size,
				{ index -> model.breedImages[index] }
			) {
				if (it == 0) {
					Spacer(modifier = Modifier.padding(top = smallDimensions.marginNormal))
				} else if (it == model.breedImages.size - 1) {
					Spacer(modifier = Modifier.padding(bottom = smallDimensions.marginNormal))
				}
				AsyncImage(
					model = model.breedImages[it],
					contentDescription = null,
					modifier = Modifier
						.fillMaxSize()
						.padding(vertical = smallDimensions.marginNormal, horizontal = smallDimensions.marginLarge)
						.testTag("BreedImage"),
					contentScale = ContentScale.Crop,
					error = painterResource(id = R.drawable.ic_error),
				)
			}
		}
	}

}
