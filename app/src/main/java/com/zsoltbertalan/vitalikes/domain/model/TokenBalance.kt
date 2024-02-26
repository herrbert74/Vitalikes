package com.zsoltbertalan.vitalikes.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenBalance(
	val label: String = "",
	val balance: String = "",
)
