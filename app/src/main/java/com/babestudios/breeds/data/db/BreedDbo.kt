package com.babestudios.breeds.data.db

import com.babestudios.breeds.domain.model.Breed
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class BreedDbo() : RealmObject {

	constructor(
		breed: String,
		subBreeds: List<String>,
	) : this() {
		this.breed = breed
		this.subBreeds = subBreeds.toRealmList()
	}

	@PrimaryKey
	var breed: String = ""
	var subBreeds: RealmList<String> = emptyList<String>().toRealmList()

}

fun Breed.toDbo(): BreedDbo = BreedDbo(
	breed = this.breed,
	subBreeds = this.subBreeds,
)

fun BreedDbo.toBreed(): Breed = Breed(
	breed = this.breed,
	subBreeds = this.subBreeds,
)