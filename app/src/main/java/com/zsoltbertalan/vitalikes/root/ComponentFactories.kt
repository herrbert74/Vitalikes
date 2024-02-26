package com.zsoltbertalan.vitalikes.root

import com.zsoltbertalan.vitalikes.domain.api.VitalikesRepository
import com.zsoltbertalan.vitalikes.ui.tokenBalancedetails.IntroComponent
import com.zsoltbertalan.vitalikes.ui.tokens.TokensComponent
import com.zsoltbertalan.vitalikes.ui.tokens.TokensExecutor
import kotlinx.coroutines.CoroutineDispatcher

internal fun createIntroFactory(
	mainContext: CoroutineDispatcher,
): CreateIntroComp = { childContext, finishHandler, output ->
	IntroComponent(
		componentContext = childContext,
		mainDispatcher = mainContext,
		output = output,
		finishHandler = finishHandler,
	)
}

/**
* These are higher order functions with common parameters used in the RootComponent,
* that return functions, that create the Decompose feature components from feature specific parameters.
*/

internal fun createTokensFactory(
	vitalikesRepository: VitalikesRepository,
	mainContext: CoroutineDispatcher,
	ioContext: CoroutineDispatcher,
): CreateTokensComp = { childContext, output ->
	TokensComponent(
		componentContext = childContext,
		mainDispatcher = mainContext,
		tokensExecutor = TokensExecutor(vitalikesRepository, mainContext, ioContext),
		output = output,
	)
}
