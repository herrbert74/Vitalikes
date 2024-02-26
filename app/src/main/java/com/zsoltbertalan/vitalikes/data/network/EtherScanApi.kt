package com.zsoltbertalan.vitalikes.data.network

import com.zsoltbertalan.vitalikes.data.network.dto.TokenBalanceDto
import retrofit2.http.GET
import retrofit2.http.Query

interface EtherScanApi {

	@GET("/api?module=account&action=tokenbalance&tag=latest")
	suspend fun getTokenBalance(
		@Query("contractaddress") contractAddress: String,
		@Query("address") address: String,
		@Query("apiKey") apiKey: String
	): TokenBalanceDto

}
