package com.zsoltbertalan.vitalikes.data.db

import com.zsoltbertalan.vitalikes.data.network.dto.TopTokenDto
import com.zsoltbertalan.vitalikes.domain.model.Token
import kotlinx.coroutines.flow.Flow

interface TokenDataSource {

	suspend fun purgeDatabase()

	suspend fun insertTokens(tokens: List<TopTokenDto>)

	fun getTokens(): List<Token>

	fun getTokensFlow(searchQuery: String): Flow<List<TokenDbo>>

	suspend fun updateTokenWithResult(token: Token, result: String)

}
