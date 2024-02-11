package com.babestudios.breeds.data.network.model

import com.babestudios.breeds.domain.model.Breed
import com.babestudios.breeds.testhelper.BreedsDtoMother
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Test

class MappersTest {

	private var mappedResponse :List<Breed> = emptyList()

	@Before
	fun setup() {
		val responseDto = BreedsDtoMother.createBreedsResponse()
		mappedResponse = responseDto.toBreeds()
	}

	@Test
	fun `when there is a response then breed is mapped`() {
		mappedResponse[0].breed shouldBe "affenpischer"
		mappedResponse[5].breed shouldBe "australian"
	}

	@Test
	fun `when there is a response then sub breed is mapped`() {
		mappedResponse[0].subBreeds shouldBe emptyList()
		mappedResponse[5].subBreeds shouldBe listOf("kelpie", "shepherd")
	}

}
