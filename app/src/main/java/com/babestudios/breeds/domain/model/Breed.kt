package com.babestudios.breeds.domain.model

import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Breed(val breed: String = "", val subBreeds: List<String>) : Parcelable
