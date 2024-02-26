package com.zsoltbertalan.vitalikes.data.db

import com.zsoltbertalan.vitalikes.data.network.dto.TopTokenDto
import com.zsoltbertalan.vitalikes.domain.model.Token
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class TokenDbo() : RealmObject {

	constructor(
		symbol: String,
		address: String,
		name: String,
		decimals: String,
		image: String,
		result: String,
	) : this() {
		this.symbol = symbol
		this.address = address
		this.name = name
		this.decimals = decimals
		this.image = image
		this.result = result
	}

	@PrimaryKey
	var symbol: String = ""
	var address: String = ""
	var name: String = ""
	var decimals: String = ""
	var image: String = ""
	var result: String = ""

}

fun TopTokenDto.toDbo(): TokenDbo = TokenDbo(
	address = this.address,
	name = this.name ?: "",
	symbol = this.symbol ?: "",
	decimals = this.decimals ?: "",
	image = this.image ?: "",
	result = "",
)

fun TokenDbo.toToken(): Token = Token(
	address = this.address,
	name = this.name,
	symbol = this.symbol,
	decimals = this.decimals,
	image = this.image,
	result = this.result,
)