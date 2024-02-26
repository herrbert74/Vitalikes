package com.zsoltbertalan.vitalikes.ui.tokenBalancedetails

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.zsoltbertalan.vitalikes.WALLET_ADDRESS
import com.zsoltbertalan.vitalikes.design.VitalikesTheme
import com.zsoltbertalan.vitalikes.design.VitalikesTypography
import kotlinx.coroutines.Dispatchers

@Composable
fun IntroScreen(component: IntroComp) {

	BackHandler(onBack = { component.onBackClicked() })

	Scaffold(
		topBar = {
			TopAppBar(
				colors = TopAppBarDefaults.topAppBarColors(
					containerColor = VitalikesTheme.colorScheme.primaryContainer,
					titleContentColor = VitalikesTheme.colorScheme.primary,
				),
				title = {
					Text("Intro")
				},
			)
		}
	) { innerPadding ->
		Column(
			modifier = Modifier
				.padding(innerPadding)
				.fillMaxSize()
		) {
			Spacer(modifier = Modifier.weight(1f))
			Text(
				text = "Wallet Address",
				textAlign = TextAlign.Center,
				modifier = Modifier
					.padding(16.dp)
					.fillMaxWidth(),
				style = VitalikesTypography.titleMedium
			)
			Text(
				text = WALLET_ADDRESS,
				textAlign = TextAlign.Center,
				modifier = Modifier
					.padding(16.dp)
					.fillMaxWidth(),
				style = VitalikesTypography.bodyMedium
			)
			Spacer(modifier = Modifier.weight(1f))
			Button(
				modifier = Modifier.padding(16.dp),
				onClick = { component.onForwardClicked() }
			) {
				Text("ERC20 Tokens")
			}
		}
	}

}

@Preview
@Composable
fun IntroScreenPreview() {
	val componentContext = DefaultComponentContext(lifecycle = LifecycleRegistry())
	IntroScreen(
		IntroComponent(componentContext, Dispatchers.Main, {}) {}
	)
}
