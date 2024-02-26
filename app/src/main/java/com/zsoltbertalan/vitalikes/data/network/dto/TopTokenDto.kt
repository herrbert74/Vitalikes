package com.zsoltbertalan.vitalikes.data.network.dto

data class TopTokenDto(
	val address: String,
	val name: String?,
	val symbol: String?,
	val decimals: String?,
	val image: String?,
)