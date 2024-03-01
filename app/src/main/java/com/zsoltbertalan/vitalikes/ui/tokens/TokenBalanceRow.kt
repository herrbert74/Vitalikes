package com.zsoltbertalan.vitalikes.ui.tokens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import com.babestudios.base.compose.design.smallDimensions
import com.zsoltbertalan.vitalikes.design.VitalikesTheme
import com.zsoltbertalan.vitalikes.design.VitalikesTypography
import com.zsoltbertalan.vitalikes.domain.model.TokenBalance
import java.math.BigDecimal

@Composable
fun TokenBalanceRow(tokenBalance: TokenBalance) {

	Row(modifier = Modifier
		.background(VitalikesTheme.colorScheme.surface)
		.padding(vertical = smallDimensions.marginNormal, horizontal = smallDimensions.marginLarge)
		.testTag("TokensRow")
	) {

		Text(
			text = tokenBalance.label,
			style = VitalikesTypography.bodyMedium,
			modifier = Modifier.weight(1.0f)
				.padding(
					top = smallDimensions.marginNormal,
					bottom = smallDimensions.marginNormal,
					end = smallDimensions.marginNormal
				)
		)

		Text(
			text = tokenBalance.balanceString,
			style = VitalikesTypography.bodyMedium,
			color = if(tokenBalance.balance == BigDecimal.ZERO) Color.Red else Color(2,82,2),
			modifier = Modifier.weight(1.0f)
				.padding(
					top = smallDimensions.marginNormal,
					bottom = smallDimensions.marginNormal,
					end = smallDimensions.marginNormal
				)
		)

	}

}
