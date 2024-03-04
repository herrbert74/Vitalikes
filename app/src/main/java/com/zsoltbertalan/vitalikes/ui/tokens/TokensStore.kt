package com.zsoltbertalan.vitalikes.ui.tokens

import androidx.compose.runtime.Immutable
import com.arkivanov.mvikotlin.core.store.Store
import com.zsoltbertalan.vitalikes.domain.model.NotSerializable
import com.zsoltbertalan.vitalikes.domain.model.TokenBalance
import com.zsoltbertalan.vitalikes.ui.tokens.TokensStore.Intent
import com.zsoltbertalan.vitalikes.ui.tokens.TokensStore.State
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.Serializable

interface TokensStore : Store<Intent, State, Nothing> {

	sealed class Intent {
		data class SearchQueryChanged(val searchQuery: String) : Intent()
	}

	@Serializable
	@Immutable
	data class State(
		val tokens: ImmutableList<TokenBalance> = listOf<TokenBalance>().toImmutableList(),
		val isLoading: Boolean = false,
		@Serializable( with = NotSerializable::class )
		val error: Throwable? = null
	)

}