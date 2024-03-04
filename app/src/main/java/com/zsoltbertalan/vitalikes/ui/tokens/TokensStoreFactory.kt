package com.zsoltbertalan.vitalikes.ui.tokens

import com.arkivanov.essenty.statekeeper.StateKeeper
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.zsoltbertalan.vitalikes.domain.model.TokenBalance
import com.zsoltbertalan.vitalikes.ui.tokens.TokensStore.Intent
import com.zsoltbertalan.vitalikes.ui.tokens.TokensStore.State

class TokensStoreFactory(
	private val storeFactory: StoreFactory,
	private val tokensExecutor: TokensExecutor,
) {

	fun create(stateKeeper: StateKeeper): TokensStore =
		object : TokensStore, Store<Intent, State, Nothing> by storeFactory.create(
			name = "TokensStore",
			initialState = stateKeeper.consume(key = "TokensStore", strategy = State.serializer()) ?: State(),
			executorFactory = { tokensExecutor },
			bootstrapper = TokensBootstrapper(),
			reducer = TokensReducer
		) {
		}.also {
			stateKeeper.register(key = "TokensStore", strategy = State.serializer()) {
				it.state.copy()
			}
		}

	private class TokensBootstrapper : CoroutineBootstrapper<BootstrapIntent>() {
		override fun invoke() {
			dispatch(BootstrapIntent.Start)
		}
	}

	private object TokensReducer : Reducer<State, Message> {
		override fun State.reduce(msg: Message): State =
			when (msg) {
				Message.ShowLoading -> copy(isLoading = true, tokens = emptyList(), error = null)
				is Message.ShowTokens -> copy(isLoading = false, tokens = msg.tokens, error = null)
				is Message.ShowError -> copy(isLoading = false, tokens = emptyList(), error = msg.throwable)
			}
	}

}

sealed class BootstrapIntent {
	data object Start : BootstrapIntent()
}

sealed class Message {
	data object ShowLoading : Message()
	data class ShowTokens(val tokens: List<TokenBalance>) : Message()
	data class ShowError(val throwable: Throwable) : Message()
}
