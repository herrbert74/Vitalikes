package com.zsoltbertalan.vitalikes.root

import kotlinx.serialization.Serializable

@Serializable
sealed class Configuration {

	@Serializable
	data object Tokens : Configuration()

	@Serializable
	data object Intro : Configuration()

}
