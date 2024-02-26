package com.zsoltbertalan.vitalikes.ui.tokenBalancedetails

import com.arkivanov.decompose.ComponentContext
import com.zsoltbertalan.vitalikes.di.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

interface IntroComp {

	fun onBackClicked()

	fun onForwardClicked()

	sealed class Output {
		data object Forward : Output()
	}

}

class IntroComponent(
	componentContext: ComponentContext,
	@MainDispatcher private val mainDispatcher: CoroutineDispatcher,
	private val output: FlowCollector<IntroComp.Output>,
	private val finishHandler: () -> Unit,
) : IntroComp, ComponentContext by componentContext {

	override fun onBackClicked() {
		CoroutineScope(mainDispatcher).launch {
			finishHandler.invoke()
		}
	}

	override fun onForwardClicked() {
		CoroutineScope(mainDispatcher).launch {
			output.emit(IntroComp.Output.Forward)
		}
	}

}
