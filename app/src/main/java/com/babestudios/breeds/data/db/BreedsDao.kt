package com.babestudios.breeds.data.db

import com.babestudios.breeds.domain.model.Breed
import com.babestudios.breeds.ext.apiRunCatching
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import javax.inject.Inject

class BreedsDao @Inject constructor(private val realm: Realm) : BreedsDataSource {

	override suspend fun purgeDatabase() {
		realm.write {
			val allPhotos = this.query(BreedDbo::class).find()
			delete(allPhotos)
		}
	}

	override suspend fun insertBreeds(breeds: List<Breed>) {
		apiRunCatching {
			realm.write {
				breeds.map { copyToRealm(it.toDbo(), UpdatePolicy.ALL) }
			}
		}
	}

	override fun getBreeds(): List<Breed> {
		return realm.query(BreedDbo::class).find().toList().map { dbo -> dbo.toBreed() }
	}

}
