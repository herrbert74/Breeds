package com.babestudios.breeds.root

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed class Configuration : Parcelable {

    @Parcelize
    data object Breeds : Configuration()

    @Parcelize
    internal data class BreedImages(val selectedBreed: String, val selectedSubBreed: String?) : Configuration()

}
