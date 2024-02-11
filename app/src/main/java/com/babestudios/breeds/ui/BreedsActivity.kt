package com.babestudios.breeds.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.DefaultComponentContext
import com.babestudios.breeds.domain.api.BreedsRepository
import com.babestudios.breeds.di.IoDispatcher
import com.babestudios.breeds.di.MainDispatcher
import com.babestudios.breeds.root.BreedsRootComponent
import com.babestudios.breeds.root.BreedsRootContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@AndroidEntryPoint
class BreedsActivity : ComponentActivity() {

	@Inject
	lateinit var breedsRepository: BreedsRepository

	@Inject
	@MainDispatcher
	lateinit var mainContext: CoroutineDispatcher

	@Inject
	@IoDispatcher
	lateinit var ioContext: CoroutineDispatcher

	private lateinit var breedsRootComponent: BreedsRootComponent

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		breedsRootComponent = BreedsRootComponent(
			DefaultComponentContext(lifecycle, savedStateRegistry, viewModelStore, null),
			mainContext,
			ioContext,
			breedsRepository
		) { finish() }

		setContent {
			BreedsRootContent(breedsRootComponent)
		}
	}

}
