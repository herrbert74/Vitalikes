package com.zsoltbertalan.vitalikes.ui

import com.zsoltbertalan.vitalikes.data.network.EtherExplorerApi
import com.zsoltbertalan.vitalikes.data.VitalikesAccessor
import com.zsoltbertalan.vitalikes.data.db.TokenDataSource
import com.zsoltbertalan.vitalikes.data.db.TokenDbo
import com.zsoltbertalan.vitalikes.data.network.EtherScanApi
import com.zsoltbertalan.vitalikes.data.network.dto.TokenBalanceDto
import com.zsoltbertalan.vitalikes.data.network.dto.TopTokenDto
import com.zsoltbertalan.vitalikes.data.network.dto.TopTokensResponseDto
import com.zsoltbertalan.vitalikes.domain.model.Token
import com.zsoltbertalan.vitalikes.ui.tokens.TokensExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

val defaultEtherExplorerApi = object : EtherExplorerApi {
	override suspend fun getTopTokens(limit: Int, apiKey: String): TopTokensResponseDto {
		TODO("Not yet implemented")
	}
}

val defaultEtherScanApi = object : EtherScanApi {
	override suspend fun getTokenBalance(contractAddress: String, address: String, apiKey: String): TokenBalanceDto {
		TODO("Not yet implemented")
	}
}

val defaultTokenDataSource = object : TokenDataSource {

	override suspend fun purgeDatabase() {
		TODO("Not yet implemented")
	}

	override suspend fun insertTokens(tokens: List<TopTokenDto>) {
		TODO("Not yet implemented")
	}

	override fun getTokens(): List<Token> {
		TODO("Not yet implemented")
	}

	override fun getTokensFlow(searchQuery: String): Flow<List<TokenDbo>> {
		TODO("Not yet implemented")
	}

	override suspend fun updateTokenWithResult(token: Token, result: String) {
		TODO("Not yet implemented")
	}

}

fun defaultTokensExecutor() = TokensExecutor(
	VitalikesAccessor(defaultEtherExplorerApi, defaultEtherScanApi, defaultTokenDataSource),
	Dispatchers.Main, Dispatchers.IO
)
