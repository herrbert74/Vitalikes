package com.zsoltbertalan.vitalikes.data

import com.zsoltbertalan.vitalikes.ETHER_SCAN_API_KEY
import com.zsoltbertalan.vitalikes.ETH_EXPLORER_API_KEY
import com.zsoltbertalan.vitalikes.WALLET_ADDRESS
import com.zsoltbertalan.vitalikes.data.db.TokenDataSource
import com.zsoltbertalan.vitalikes.data.db.toDbo
import com.zsoltbertalan.vitalikes.data.db.toToken
import com.zsoltbertalan.vitalikes.data.network.EtherExplorerApi
import com.zsoltbertalan.vitalikes.data.network.EtherScanApi
import com.zsoltbertalan.vitalikes.data.network.dto.TopTokenDto
import com.zsoltbertalan.vitalikes.domain.api.VitalikesRepository
import com.zsoltbertalan.vitalikes.domain.model.INVALID_DIGITS
import com.zsoltbertalan.vitalikes.domain.model.TokenBalance
import com.zsoltbertalan.vitalikes.domain.model.toTokenBalance
import com.zsoltbertalan.vitalikes.ext.apiRunCatching
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import timber.log.Timber
import javax.inject.Singleton

@Singleton
class VitalikesAccessor(
	private val etherExplorerApi: EtherExplorerApi,
	private val etherScanApi: EtherScanApi,
	private val tokenDataSource: TokenDataSource,
) : VitalikesRepository {

	/**
	 * Get the top token list from the database if it's already there, from Ethplorer otherwise.
	 * Get the token balances with searchQuery in the symbol fields fro EtherScan, or, if it fails, from the database.
	 */
	override suspend fun updateTokensWithQuery(searchQuery: String) {

		val tokensFromDb = tokenDataSource.getTokens()

		var tokensInDatabase = true
		val tokens = tokensFromDb.ifEmpty {
			tokensInDatabase = false
			getTopTokens().map { it.toDbo().toToken() }
		}

		if (tokensInDatabase) getTopTokens() //Only used next time, so updates might be missed (should be rare)

		val filteredTokens = tokens.filter {
			it.symbol.isNotEmpty() && it.symbol.contains(searchQuery, true)
		}

		coroutineScope {
			filteredTokens.map { token ->
				async {
					apiRunCatching {
						flow { emit(etherScanApi.getTokenBalance(token.address, WALLET_ADDRESS, ETHER_SCAN_API_KEY)) }
							.retryWhen { _, attempt ->
								if (attempt < 3) {
									if (attempt > 0) delay(1000L)
									return@retryWhen true
								} else {
									return@retryWhen false
								}
							}
							.catch {
								Timber.d("VitalikAccessor Loading failed while loading: ${token.symbol} exception: " +
									"${it.message}")
							}
							.first().also { balance ->
								if (balance.status == 1L) {
									tokenDataSource.updateTokenWithResult(token, balance.result)
								} else {
									throw Exception("")
								}
							}
					}
				}
			}.awaitAll()
		}
	}

	private suspend fun getTopTokens(): List<TopTokenDto> {
		return etherExplorerApi.getTopTokens(100, ETH_EXPLORER_API_KEY).tokens.also {
			tokenDataSource.insertTokens(it)
		}
	}

	override suspend fun getTokensFlow(searchQuery: String): Flow<List<TokenBalance>> {
		return tokenDataSource.getTokensFlow(searchQuery)
			.map { list ->
				list
					.filter { token -> token.symbol.contains(searchQuery, true) }
					.map { dbo -> dbo.toToken().toTokenBalance() }
					.filterNot { token -> token.balance.startsWith(INVALID_DIGITS) }
			}
	}

}
