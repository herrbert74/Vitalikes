package com.zsoltbertalan.vitalikes.data.db

import com.zsoltbertalan.vitalikes.data.network.dto.TopTokenDto
import com.zsoltbertalan.vitalikes.domain.model.Token
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenDao @Inject constructor(private val realm: Realm) : TokenDataSource {

	override suspend fun purgeDatabase() {
		realm.write {
			val allTokens = this.query(TokenDbo::class).find()
			delete(allTokens)
		}
	}

	/**
	 * Insert [TopTokenDto]s while retaining the result param, that came from a later call.
	 */
	override suspend fun insertTokens(tokens: List<TopTokenDto>) {
		tokens.forEach { token ->
			val managedTokenDbo = getToken(token.symbol)
			realm.write {
				val tokenWithResult = token.toDbo().retainTokenResult(managedTokenDbo)
				tokens.map { copyToRealm(tokenWithResult, UpdatePolicy.ALL) }
			}
		}
	}

	private fun getToken(symbol: String?): TokenDbo? =
		realm.query(TokenDbo::class, "symbol == $0", symbol).first().find()

	private fun TokenDbo.retainTokenResult(matchingTokenDbo: TokenDbo?): TokenDbo = apply {
		result = matchingTokenDbo?.result ?: ""
	}

	override fun getTokens(): List<Token> {
		return realm.query(TokenDbo::class).find().toList().map { dbo -> dbo.toToken() }
	}

	override fun getTokensFlow(searchQuery: String): Flow<List<TokenDbo>> {
		return realm.query(TokenDbo::class)
			.asFlow()
			.map { changes -> changes.list }
	}

	override suspend fun updateTokenWithResult(token: Token, result: String) {
		realm.write {
			val matchingToken = this.query(TokenDbo::class, "symbol == $0", token.symbol).first().find()
			matchingToken?.let {
				it.result = result
				copyToRealm(it, UpdatePolicy.ALL)
			}
		}
	}

}
