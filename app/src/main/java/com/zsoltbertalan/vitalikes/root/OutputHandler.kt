package com.zsoltbertalan.vitalikes.root

import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.zsoltbertalan.vitalikes.ui.intro.IntroComp
import com.zsoltbertalan.vitalikes.ui.tokens.TokensComp

val navigation = StackNavigation<Configuration>()

internal fun onIntroOutput(output: IntroComp.Output): Unit = when (output) {
	IntroComp.Output.Forward -> navigation.push(Configuration.Tokens)
}

internal fun onTokensOutput(output: TokensComp.Output): Unit = when (output) {
	TokensComp.Output.Back -> navigation.pop()
}
