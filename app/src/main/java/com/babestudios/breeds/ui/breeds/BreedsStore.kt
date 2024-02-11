package com.babestudios.breeds.ui.breeds

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.mvikotlin.core.store.Store
import com.babestudios.breeds.ui.breeds.BreedsStore.Intent
import com.babestudios.breeds.ui.breeds.BreedsStore.State
import com.babestudios.breeds.domain.model.Breed
import kotlinx.parcelize.Parcelize

interface BreedsStore : Store<Intent, State, Nothing> {

	sealed class Intent {
		data object SortBreedsClicked : Intent()
		data object RetryClicked : Intent()
	}

	@Parcelize
	data class State(val breeds: List<Breed> = emptyList(), val error: Throwable? = null) : Parcelable

}