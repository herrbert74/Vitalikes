package com.zsoltbertalan.vitalikes.ui.tokens

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.zsoltbertalan.vitalikes.di.MainDispatcher
import com.zsoltbertalan.vitalikes.ext.asValue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

interface TokensComp {

	fun onBackClicked()

	fun onSearchQueryChanged(searchQuery: String)

	val state: Value<TokensStore.State>

	fun setSearchMenuItemExpanded()
	fun setSearchMenuItemCollapsed()

	sealed class Output {
		data object Back : Output()
	}

}

class TokensComponent(
	componentContext: ComponentContext,
	@MainDispatcher private val mainDispatcher: CoroutineDispatcher,
	val tokensExecutor: TokensExecutor,
	private val output: FlowCollector<TokensComp.Output>,
) : TokensComp, ComponentContext by componentContext {

	private var tokensStore: TokensStore =
		TokensStoreFactory(LoggingStoreFactory(DefaultStoreFactory()), tokensExecutor).create(stateKeeper)

	override fun onBackClicked() {
		CoroutineScope(mainDispatcher).launch {
			output.emit(TokensComp.Output.Back)
		}
	}

	override fun onSearchQueryChanged(searchQuery: String) {
		tokensStore.accept(TokensStore.Intent.SearchQueryChanged(searchQuery))
	}

	override fun setSearchMenuItemExpanded() {
		//mainStore.accept(MainStore.Intent.SetSearchMenuItemExpanded)
	}

	override fun setSearchMenuItemCollapsed() {
		//mainStore.accept(MainStore.Intent.SetSearchMenuItemCollapsed)
	}

	override val state: Value<TokensStore.State>
		get() = tokensStore.asValue()

}
