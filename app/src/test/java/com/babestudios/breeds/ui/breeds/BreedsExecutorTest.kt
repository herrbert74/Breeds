package com.babestudios.breeds.ui.breeds

import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.babestudios.base.kotlin.ext.test
import com.babestudios.breeds.domain.api.BreedsRepository
import com.babestudios.breeds.testhelper.BreedsMother
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Test

class BreedsExecutorTest {

	private val breedsRepository = mockk<BreedsRepository>()

	private lateinit var breedsExecutor: BreedsExecutor

	private lateinit var breedsStore: BreedsStore

	private val testCoroutineDispatcher = Dispatchers.Unconfined

	@Before
	fun setup() {
		coEvery { breedsRepository.getAllBreeds() } answers { BreedsMother.createBreedsList() }

		breedsExecutor = BreedsExecutor(
			breedsRepository,
			testCoroutineDispatcher,
			testCoroutineDispatcher
		)

		breedsStore =
			BreedsStoreFactory(DefaultStoreFactory(), breedsExecutor).create(stateKeeper = StateKeeperDispatcher())
	}

	@Test
	fun `when started then get breeds is called and returns correct list`() {
		val states = breedsStore.states.test()

		coVerify(exactly = 1) { breedsRepository.getAllBreeds() }
		states.first().breeds shouldBe BreedsMother.createBreedsList().value
	}

	@Test
	fun `when sort button is pressed then get breeds returned in reverse order`() {
		val states = breedsStore.states.test()

		breedsStore.accept(BreedsStore.Intent.SortBreedsClicked)

		states.last().breeds shouldBe BreedsMother.createBreedsList().value.reversed()
	}

}