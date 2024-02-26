package com.zsoltbertalan.vitalikes.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.zsoltbertalan.vitalikes.design.VitalikesTheme
import com.zsoltbertalan.vitalikes.ui.tokenBalancedetails.IntroScreen
import com.zsoltbertalan.vitalikes.ui.tokens.TokensScreen

@Composable
fun VitalikesRootContent(component: VitalikesRootComp) {

	val stack = component.childStackValue

	VitalikesTheme {
		Children(stack = stack, animation = stackAnimation(scale())) {
			when (val child = it.instance) {
				is VitalikesChild.Intro -> IntroScreen(child.component)
				is VitalikesChild.Tokens -> TokensScreen(child.component)
			}
		}
	}

}
