package com.zsoltbertalan.vitalikes.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.zsoltbertalan.vitalikes.domain.api.VitalikesRepository
import com.zsoltbertalan.vitalikes.di.IoDispatcher
import com.zsoltbertalan.vitalikes.di.MainDispatcher
import com.zsoltbertalan.vitalikes.ui.tokenBalancedetails.IntroComp
import com.zsoltbertalan.vitalikes.ui.tokens.TokensComp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.FlowCollector

typealias CreateIntroComp = (ComponentContext, () -> Unit, FlowCollector<IntroComp.Output>) -> IntroComp
typealias CreateTokensComp = (ComponentContext, FlowCollector<TokensComp.Output>) -> TokensComp

interface VitalikesRootComp {
	val childStackValue: Value<ChildStack<*, VitalikesChild>>
}

class VitalikesRootComponent internal constructor(
	componentContext: ComponentContext,
	private val finishHandler: () -> Unit,
	private val createIntroComp: CreateIntroComp,
	private val createTokensComp: CreateTokensComp,
) : VitalikesRootComp,
	ComponentContext by componentContext {

	constructor(
		componentContext: ComponentContext,
		@MainDispatcher mainContext: CoroutineDispatcher,
		@IoDispatcher ioContext: CoroutineDispatcher,
		vitalikesRepository: VitalikesRepository,
		finishHandler: () -> Unit,
	) : this(
		componentContext = componentContext,
		finishHandler,
		createIntroComp = createIntroFactory(mainContext),
		createTokensComp = createTokensFactory(vitalikesRepository, mainContext, ioContext),
	)

	private val stack = childStack(
		source = navigation,
		initialStack = { listOf(Configuration.Intro) },
		saveStack = { null },
		restoreStack = { null },
		childFactory = ::createChild
	)

	override val childStackValue = stack

	private fun createChild(configuration: Configuration, componentContext: ComponentContext): VitalikesChild =
		when (configuration) {

			is Configuration.Intro -> VitalikesChild.Intro(
				createIntroComp(
					componentContext.childContext(key = "IntroComponent"),
					finishHandler,
					FlowCollector(::onIntroOutput)
				)
			)

			is Configuration.Tokens -> VitalikesChild.Tokens(
				createTokensComp(
					componentContext.childContext(key = "TokensComponent"),
					FlowCollector(::onTokensOutput)
				)
			)

		}

}
