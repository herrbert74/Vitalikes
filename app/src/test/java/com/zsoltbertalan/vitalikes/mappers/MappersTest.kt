package com.zsoltbertalan.vitalikes.mappers

import com.zsoltbertalan.vitalikes.domain.model.TokenBalance
import com.zsoltbertalan.vitalikes.domain.model.toTokenBalance
import com.zsoltbertalan.vitalikes.testhelper.TokenMother
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Test

class MappersTest {

	private var mappedResponse: List<TokenBalance> = emptyList()

	@Before
	fun setup() {
		val responseDto = TokenMother.createTokenList()
		mappedResponse = responseDto.map { it.toTokenBalance() }
	}

	@Test
	fun `when there is a response then name is mapped`() {
		mappedResponse[0].label shouldBe "USDC Balance:"
		mappedResponse[0].balance shouldBe "8910.442234 USDC"
	}

}
