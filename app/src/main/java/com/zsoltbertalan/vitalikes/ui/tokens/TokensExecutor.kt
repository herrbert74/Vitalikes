package com.zsoltbertalan.vitalikes.ui.tokens

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.github.michaelbull.result.onFailure
import com.zsoltbertalan.vitalikes.domain.api.VitalikesRepository
import com.zsoltbertalan.vitalikes.ext.apiRunCatching
import com.zsoltbertalan.vitalikes.ui.tokens.TokensStore.Intent
import com.zsoltbertalan.vitalikes.ui.tokens.TokensStore.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TokensExecutor(
	private val vitalikesRepository: VitalikesRepository,
	private val mainContext: CoroutineDispatcher,
	private val ioContext: CoroutineDispatcher,
) : CoroutineExecutor<Intent, BootstrapIntent, State, Message, Nothing>(mainContext) {

	private val mutableSearchQueryFlow: MutableStateFlow<String> = MutableStateFlow("")

	private val searchQueryFlow: Flow<String> = mutableSearchQueryFlow.map { it.trim() }.debounce(250)

	private var tokensJob: Job? = null

	override fun executeAction(action: BootstrapIntent, getState: () -> State) {
		super.executeAction(action, getState)
		scope.launch {
			searchQueryFlow.collect { searchQuery ->
				tokensJob?.cancel()
				if (searchQuery.length > 2) {
					showLoading()
					tokensJob = scope.launch(ioContext) {
						vitalikesRepository.getTokensFlow(searchQuery).collect {
							withContext(mainContext) { dispatch(Message.ShowTokens(it)) }
						}

					}
					scope.launch(ioContext) {
						apiRunCatching { vitalikesRepository.updateTokensWithQuery(searchQuery) }
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

	override fun executeIntent(intent: Intent, getState: () -> State) {
		when (intent) {
			is Intent.SearchQueryChanged -> {
				scope.launch {
					mutableSearchQueryFlow.emit(intent.searchQuery)
				}
			}
		}
	}

	private fun showLoading() {
		dispatch(Message.ShowLoading)
	}

}
