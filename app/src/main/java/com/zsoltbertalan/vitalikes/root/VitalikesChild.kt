package com.zsoltbertalan.vitalikes.root

import com.zsoltbertalan.vitalikes.ui.tokenBalancedetails.IntroComp
import com.zsoltbertalan.vitalikes.ui.tokens.TokensComp

sealed class VitalikesChild {
    data class Intro(val component: IntroComp) : VitalikesChild()
    data class Tokens(val component: TokensComp) : VitalikesChild()
}
