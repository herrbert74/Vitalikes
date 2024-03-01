package com.zsoltbertalan.vitalikes.domain.model

import java.math.BigDecimal
import kotlin.math.pow

/**
 * Intermediary class for Accessor only, with all the raw data.
 * The presentation layer uses [TokenBalance].
 */
data class Token(
	val symbol: String,
	val address: String,
	val name: String?,
	val decimals: String?,
	val image: String?,
	val result: String,
)

fun Token.toTokenBalance() = TokenBalance(
	label = "$symbol Balance:",
	balance = this.result.toBigDecimalOrNull() ?: BigDecimal.ZERO,
	balanceString = "${getBalanceAmount(result, decimals)} $symbol",
)

const val INVALID_DIGITS = "%INVALID%"

fun getBalanceAmount(result: String, decimals: String?): String {
	val decimalLength = decimals?.toLongOrNull() ?: return INVALID_DIGITS

	return try {
		val amount = result.toBigDecimal().divide(10.toDouble().pow(decimalLength.toDouble()).toBigDecimal())
		if(amount.toInt() == 0) "0" else "%.${decimalLength}f".format(amount)
	} catch (e: NumberFormatException) {
		INVALID_DIGITS
	}
}
