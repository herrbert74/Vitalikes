package com.zsoltbertalan.vitalikes.ui.tokens

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.github.michaelbull.result.onFailure
import com.zsoltbertalan.vitalikes.domain.api.VitalikesRepository
import com.zsoltbertalan.vitalikes.ext.apiRunCatching
import com.zsoltbertalan.vitalikes.ui.tokens.TokensStore.Intent
import com.zsoltbertalan.vitalikes.ui.tokens.TokensStore.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TokensExecutor(
	private val vitalikesRepository: VitalikesRepository,
	private val mainContext: CoroutineDispatcher,
	private val ioContext: CoroutineDispatcher,
) : CoroutineExecutor<Intent, Nothing, State, Message, Nothing>(mainContext) {

	private var tokensJob: Job? = null

	override fun executeIntent(intent: Intent, getState: () -> State) {
		when (intent) {
			is Intent.SearchQueryChanged -> {
				tokensJob?.cancel()
				if (intent.searchQuery.length > 2) {
					showLoading()
					tokensJob = scope.launch(ioContext) {
						vitalikesRepository.getTokensFlow(intent.searchQuery).collect {
							withContext(mainContext) { dispatch(Message.ShowTokens(it)) }
						}

					}
					scope.launch(ioContext) {
						apiRunCatching { vitalikesRepository.updateTokensWithQuery(intent.searchQuery) }
							.onFailure {
								withContext(mainContext) { dispatch(Message.ShowError(it)) }
							}
					}
				} else {
					dispatch(Message.ShowTokens(emptyList()))
				}
			}
		}
	}

	private fun showLoading() {
		dispatch(Message.ShowLoading)
	}

}
