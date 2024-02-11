package com.babestudios.breeds.ui.breeds

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.babestudios.breeds.R
import com.babestudios.breeds.design.BreedsTheme
import com.babestudios.breeds.design.BreedsTypography
import com.babestudios.breeds.design.smallDimensions
import com.babestudios.breeds.testhelper.BreedsMother
import com.babestudios.breeds.ui.breeds.list.ExpandableLazyColumn
import com.babestudios.breeds.ui.defaultBreedsExecutor

@Composable
fun BreedsScreen(component: BreedsComp) {

	val model by component.state.subscribeAsState()

	BackHandler(onBack = { component.onBackClicked() })

	BreedsScaffold(component, model)

}

@Composable
private fun BreedsScaffold(
	component: BreedsComp,
	model: BreedsStore.State
) {
	Scaffold(
		topBar = {
			TopAppBar(
				colors = topAppBarColors(
					containerColor = BreedsTheme.colorScheme.primaryContainer,
					titleContentColor = BreedsTheme.colorScheme.primary,
				),
				title = {
					Text("Breeds")
				},
				actions = {
					IconButton(onClick = { component.onSortBreedsClicked() }) {
						Icon(
							painter = painterResource(R.drawable.ic_sort_by_alpha),
							contentDescription = "Sort icon",
						)
					}
				}
			)
		}
	) { innerPadding ->
		if (model.error == null) {
			ExpandableLazyColumn(
				modifier = Modifier.padding(innerPadding),
				sections = model.breeds,
				onHeaderClick = component::onItemClicked,
				onSubItemClick = component::onItemClicked,
			)
		} else {
			ErrorView(innerPadding, component)
		}
	}
}

@Composable
private fun ErrorView(innerPadding: PaddingValues, component: BreedsComp) {
	Column(
		Modifier
			.fillMaxSize(1f)
			.padding(innerPadding),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Image(
			painter = painterResource(R.drawable.ic_business_error),
			contentDescription = null
		)
		Text(
			"Something went wrong",
			style = BreedsTypography.titleLarge,
			modifier = Modifier
				.align(Alignment.CenterHorizontally),
		)
		Spacer(modifier = Modifier.height(smallDimensions.marginExtraLarge))
		Button(onClick = { component.onRetryClicked() }) {
			Text(
				"Retry",
			)
		}
	}
}

@Preview("Breeds Screen Preview")
@Composable
fun BreedsScreenPreview() {
	val componentContext = DefaultComponentContext(lifecycle = LifecycleRegistry())
	BreedsScaffold(
		BreedsComponent(componentContext, defaultBreedsExecutor(), {}, {}),
		BreedsStore.State(breeds = BreedsMother.createBreedsList().value)
	)
}

@Preview("Breeds Screen Error Preview")
@Composable
fun BreedsScreenErrorPreview() {
	val componentContext = DefaultComponentContext(lifecycle = LifecycleRegistry())
	BreedsScaffold(
		BreedsComponent(componentContext, defaultBreedsExecutor(), {}, {}),
		BreedsStore.State(
			breeds = BreedsMother.createBreedsList().value,
			error = RuntimeException("Something went wrong")
		)
	)
}
