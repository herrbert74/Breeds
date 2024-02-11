package com.babestudios.breeds

import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.babestudios.breeds.domain.api.BreedsRepository
import com.babestudios.breeds.di.IoDispatcher
import com.babestudios.breeds.di.MainDispatcher
import com.babestudios.breeds.root.BreedsRootComponent
import com.babestudios.breeds.root.BreedsRootContent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class BreedImagesTest {

	@get:Rule(order = 0)
	val hiltAndroidRule = HiltAndroidRule(this)

	@get:Rule
	val composeTestRule = createComposeRule()

	@Inject
	lateinit var breedsRepository: BreedsRepository

	@Inject
	@MainDispatcher
	lateinit var mainContext: CoroutineDispatcher

	@Inject
	@IoDispatcher
	lateinit var ioContext: CoroutineDispatcher

	@Before
	fun setUp() {

		hiltAndroidRule.inject()

		CoroutineScope(mainContext).launch {
			val breedsRootComponent = BreedsRootComponent(
				DefaultComponentContext(lifecycle = LifecycleRegistry()),
				mainContext,
				ioContext,
				breedsRepository,
			) {}
			composeTestRule.setContent {
				BreedsRootContent(breedsRootComponent)
			}
		}

	}

	@Test
	fun whenILaunchTheApp_ThenISeeAListOfDogBreeds() {
		composeTestRule.waitUntilAtLeastOneExists(hasTestTag("BreedsHeader"), 1000L)

		composeTestRule.onNodeWithText("Affenpinscher", ignoreCase = true).assertExists()
	}

	@Test
	fun givenIHaveLaunchedTheApp_WhenISelectABreedFromTheList_ThenISeeTenImagesOfTheBreed() {

		composeTestRule.waitUntilAtLeastOneExists(hasTestTag("BreedsHeader"), 3000L)

		composeTestRule.onNodeWithText("Affenpinscher", ignoreCase = true).performClick()

		composeTestRule.waitUntilNodeCount(hasTestTag("BreedImage"), 10, 3000L)

		Thread.sleep(400L) //Only needed for visual checking!

		composeTestRule.onAllNodesWithTag("BreedImage").assertAny(hasTestTag("BreedImage"))

	}
}