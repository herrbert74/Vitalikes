package com.zsoltbertalan.vitalikes.domain.api

import com.zsoltbertalan.vitalikes.domain.model.TokenBalance
import kotlinx.coroutines.flow.Flow

interface VitalikesRepository {
	suspend fun updateTokensWithQuery(searchQuery: String)
	suspend fun getTokensFlow(searchQuery: String): Flow<List<TokenBalance>>
}
