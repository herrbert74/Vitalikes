package com.zsoltbertalan.vitalikes.ui.tokens

import com.arkivanov.mvikotlin.core.store.Store
import com.zsoltbertalan.vitalikes.domain.model.NotSerializable
import com.zsoltbertalan.vitalikes.domain.model.TokenBalance
import com.zsoltbertalan.vitalikes.ui.tokens.TokensStore.Intent
import com.zsoltbertalan.vitalikes.ui.tokens.TokensStore.State
import kotlinx.serialization.Serializable

interface TokensStore : Store<Intent, State, Nothing> {

	sealed class Intent {
		data class SearchQueryChanged(val searchQuery: String) : Intent()
	}

	@Serializable
	data class State(
		val tokens: List<TokenBalance> = emptyList(),
		val isLoading: Boolean = false,
		@Serializable( with = NotSerializable::class )
		val error: Throwable? = null
	)

}