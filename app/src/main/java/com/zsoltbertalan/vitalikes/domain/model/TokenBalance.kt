package com.zsoltbertalan.vitalikes.domain.model

import com.zsoltbertalan.vitalikes.ext.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class TokenBalance(
	val label: String = "",
	@Serializable(with = BigDecimalSerializer::class)
	val balance: BigDecimal = BigDecimal.ZERO,
	val balanceString: String = "",
)
