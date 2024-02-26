package com.zsoltbertalan.vitalikes.testhelper

import com.zsoltbertalan.vitalikes.domain.model.Token

/**
 * This is an example of an ObjectMother that can be used in both Unit and Android UI tests.
 * As such it would go into its own module in a normal project.
 */
object TokenMother {

	fun createTokenList() = listOf(
		createDefaultToken("USDC", "0xdac17f958d2ee523a2206206994597c13d831ec7", "USD Coin", "6", result =
		"8910442234"),
		createDefaultToken("USDT", "0xdac17f958d2ee523a2206206994597c13d831ec7", "Tether USD", "18", result = "100000"),
		createDefaultToken("USDE", "0xdac17f958d2ee523a2206206994597c13d831ec7", "Ethena USDe", "18", result =
		"100000"),
	)
}

private fun createDefaultToken(
	symbol: String,
	address: String,
	name: String? = "",
	decimals: String? = "18",
	image: String? = "",
	result: String = "",
): Token = Token(symbol, address, name, decimals, image, result)
