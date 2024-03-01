package com.zsoltbertalan.vitalikes.testhelper

import com.zsoltbertalan.vitalikes.domain.model.TokenBalance
import java.math.BigDecimal

/**
 * This is an example of an ObjectMother that can be used in both Unit and Android UI tests.
 * As such it would go into its own module in a normal project.
 */
object TokenBalanceMother {

	fun createTokenBalanceList() = listOf(
		createDefaultTokenBalance("USDT Balance:", BigDecimal("200000"), "0.2 USDT"),
		createDefaultTokenBalance("USDC Balance:", BigDecimal("200000"),"0.2 USDC"),
		createDefaultTokenBalance("USDE Balance", BigDecimal.ZERO,"0 USDE"),
	)

}

private fun createDefaultTokenBalance(
	label: String,
	balance: BigDecimal,
	balanceString: String,
): TokenBalance = TokenBalance(
	label = label,
	balance = balance,
	balanceString = balanceString
)
