package com.zsoltbertalan.vitalikes

import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.zsoltbertalan.vitalikes.di.IoDispatcher
import com.zsoltbertalan.vitalikes.di.MainDispatcher
import com.zsoltbertalan.vitalikes.domain.api.VitalikesRepository
import com.zsoltbertalan.vitalikes.root.VitalikesRootComponent
import com.zsoltbertalan.vitalikes.root.VitalikesRootContent
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
class TokensTest {

	@get:Rule(order = 0)
	val hiltAndroidRule = HiltAndroidRule(this)

	@get:Rule
	val composeTestRule = createComposeRule()

	@Inject
	lateinit var vitalikesRepository: VitalikesRepository

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
			val vitalikesRootComponent = VitalikesRootComponent(
				DefaultComponentContext(lifecycle = LifecycleRegistry()),
				mainContext,
				ioContext,
				vitalikesRepository,
			) {}
			composeTestRule.setContent {
				VitalikesRootContent(vitalikesRootComponent)
			}
		}

	}

	@Test
	fun showIntro() {

		composeTestRule.onNodeWithText("ERC20 Tokens", ignoreCase = true).assertExists()

	}

	@Test
	fun showTokenBalances() {

		composeTestRule.onNodeWithText("ERC20 Tokens", ignoreCase = true).performClick()

		composeTestRule.waitUntilAtLeastOneExists(hasContentDescription("Search icon"), 3000L)

		composeTestRule.onNodeWithContentDescription("Search icon", ignoreCase = true).performClick()

		composeTestRule.waitUntilAtLeastOneExists(hasContentDescription("Search for tokens"), 3000L)

		composeTestRule.onNodeWithContentDescription("Search for tokens", ignoreCase = true).onChildAt(0)
			.performTextInput("usd")

		composeTestRule.waitUntilAtLeastOneExists(hasTestTag("TokensRow"), 3000L)

		composeTestRule.onAllNodesWithTag("TokensRow").assertAny(hasTestTag("TokensRow"))

	}

}