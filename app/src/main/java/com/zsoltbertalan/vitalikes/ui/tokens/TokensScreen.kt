package com.zsoltbertalan.vitalikes.ui.tokens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.zsoltbertalan.vitalikes.R
import com.zsoltbertalan.vitalikes.design.Colors
import com.zsoltbertalan.vitalikes.design.Dimens
import com.zsoltbertalan.vitalikes.design.VitalikesTheme
import com.zsoltbertalan.vitalikes.design.VitalikesTypography
import com.zsoltbertalan.vitalikes.design.titleLargeBold
import com.zsoltbertalan.vitalikes.testhelper.TokenBalanceMother

@Composable
fun TokensScreen(component: TokensComp) {

	val model by component.state.subscribeAsState()

	BackHandler(onBack = { component.onBackClicked() })

	TokensScaffold(component = component, model)

}

@Composable
private fun TokensScaffold(
	component: TokensComp,
	model: TokensStore.State,
) {

	var searchQuery by rememberSaveable { mutableStateOf("") }

	var isSearchBarActive by rememberSaveable { mutableStateOf(false) }

	val focusManager = LocalFocusManager.current

	val topAppBarColors = TopAppBarDefaults.topAppBarColors(
		containerColor = Colors.surface,
		scrolledContainerColor = Colors.surfaceContainer,
	)

	fun closeSearchBar() {
		focusManager.clearFocus()
		isSearchBarActive = false
	}

	Scaffold(
		topBar = {
			TopAppBar(
				colors = topAppBarColors,
				title = {
					Text("Vitalikes")
				},
				navigationIcon = {
					IconButton(onClick = { component.onBackClicked() }) {
						Icon(
							imageVector = Icons.AutoMirrored.Filled.ArrowBack,
							contentDescription = "Back",
							tint = MaterialTheme.colorScheme.onPrimaryContainer
						)
					}
				},
				actions = {
					IconButton(onClick = {
						isSearchBarActive = !isSearchBarActive
						if (isSearchBarActive) component.setSearchMenuItemExpanded()
						else component.setSearchMenuItemCollapsed()
					}) {
						Icon(
							imageVector = Icons.Filled.Search,
							contentDescription = "Search icon",
							tint = Colors.onPrimaryContainer
						)
					}
				}
			)
		}
	) { paddingValues ->
		if (isSearchBarActive) {
			SearchBar(
				query = searchQuery,
				onQueryChange = {
					searchQuery = it
					component.onSearchQueryChanged(it)
				},
				onSearch = { closeSearchBar() },
				active = true,
				modifier = Modifier.semantics { contentDescription = "Search for tokens" },
				onActiveChange = {
					isSearchBarActive = it
					if (!isSearchBarActive) focusManager.clearFocus()
				},
				placeholder = { Text(stringResource(R.string.search_prompt)) },
				leadingIcon = {
					Icon(
						modifier = Modifier.clickable {
							isSearchBarActive = false
							searchQuery = ""
							component.onSearchQueryChanged("")
						},
						imageVector = Icons.AutoMirrored.Filled.ArrowBack,
						contentDescription = null
					)
				},
			) {
				if (model.isLoading) {
					Box(
						contentAlignment = Alignment.Center,
						modifier = Modifier.fillMaxSize()
					) {
						CircularProgressIndicator()
					}
				} else if (model.error != null) {
					ErrorView(innerPadding = paddingValues)
				} else if (searchQuery.length > 2 && model.tokens.isEmpty()) {
					EmptySearchList(paddingValues)
				} else {
					TokenSearchResultList(
						items = model.tokens,
					)
				}
			}
		}
	}
}

@Composable
private fun EmptySearchList(
	paddingValues: PaddingValues,
	message: String = stringResource(R.string.no_search_result),
) {

	val viewMarginLarge = Dimens.marginLarge

	Column(
		Modifier
			.padding(paddingValues)
			.fillMaxSize(1f)
			//Matches the empty icon background from BaBeStudiosBase
			.background(colorResource(R.color.grey_1)),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Image(
			painter = painterResource(com.babestudios.base.android.R.drawable.ic_business_empty),
			contentDescription = null
		)
		Text(
			text = message,
			style = VitalikesTypography.titleLargeBold,
			textAlign = TextAlign.Center,
			modifier = Modifier
				.align(Alignment.CenterHorizontally)
				.padding(all = viewMarginLarge * 2)
		)
	}
}

@Composable
private fun ErrorView(innerPadding: PaddingValues) {
	Column(
		Modifier
			.fillMaxSize(1f)
			.padding(innerPadding),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Image(
			painter = painterResource(com.babestudios.base.android.R.drawable.ic_business_error),
			contentDescription = null
		)
		Text(
			"Something went wrong",
			style = VitalikesTypography.titleLarge,
			modifier = Modifier
				.align(Alignment.CenterHorizontally),
		)
	}
}

@Preview
@Composable
fun TokensScreenPreview() {
	VitalikesTheme {
		TokenSearchResultList(
			TokenBalanceMother.createTokenBalanceList()
		)
	}
}

@Preview
@Composable
fun EmptySearchListPreview() {
	VitalikesTheme { EmptySearchList(PaddingValues()) }
}

@Preview
@Composable
fun TokensScreenErrorPreview() {
	VitalikesTheme { ErrorView(PaddingValues()) }
}
