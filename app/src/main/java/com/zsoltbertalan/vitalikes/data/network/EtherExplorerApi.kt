package com.zsoltbertalan.vitalikes.data.network

import com.zsoltbertalan.vitalikes.data.network.dto.TopTokensResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface EtherExplorerApi {

	@GET("/getTopTokens")
	suspend fun getTopTokens(
		@Query("limit") limit: Int,
		@Query("apiKey") apiKey: String
	): TopTokensResponseDto

}
