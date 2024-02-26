package com.zsoltbertalan.vitalikes.ui

import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.babestudios.base.kotlin.ext.test
import com.zsoltbertalan.vitalikes.domain.api.VitalikesRepository
import com.zsoltbertalan.vitalikes.testhelper.TokenBalanceMother
import com.zsoltbertalan.vitalikes.ui.tokens.TokensExecutor
import com.zsoltbertalan.vitalikes.ui.tokens.TokensStore
import com.zsoltbertalan.vitalikes.ui.tokens.TokensStoreFactory
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class TokensExecutorTest {

	private val vitalikesRepository = mockk<VitalikesRepository>(relaxed = true)

	private lateinit var tokensExecutor: TokensExecutor

	private lateinit var tokensStore: TokensStore

	private val testCoroutineDispatcher = UnconfinedTestDispatcher()

	@Before
	fun setUp() {
		coEvery { vitalikesRepository.getTokensFlow("USD") } coAnswers {
			flowOf(TokenBalanceMother.createTokenBalanceList())
		}

		coEvery { vitalikesRepository.updateTokensWithQuery("USD") } returns Unit

		tokensExecutor = TokensExecutor(
			vitalikesRepository,
			testCoroutineDispatcher,
			testCoroutineDispatcher
		)

		tokensStore =
			TokensStoreFactory(DefaultStoreFactory(), tokensExecutor).create(stateKeeper = StateKeeperDispatcher())
	}

	@OptIn(ExperimentalCoroutinesApi::class)
	@Test
	fun `when search query is valid then getTokens is called and returns correct list`() =
		runTest {
			val states = tokensStore.states.test()

			tokensStore.accept(TokensStore.Intent.SearchQueryChanged("USD"))

			advanceUntilIdle()

			coVerify(exactly = 1) { vitalikesRepository.getTokensFlow("USD") }
			states.last().tokens shouldBe TokenBalanceMother.createTokenBalanceList()
		}

	@Test
	fun `when search query is short then getTokens is not called`() = runTest {

		tokensStore.accept(TokensStore.Intent.SearchQueryChanged("US"))

		coVerify(exactly = 0) { vitalikesRepository.getTokensFlow("USD") }

	}

}


