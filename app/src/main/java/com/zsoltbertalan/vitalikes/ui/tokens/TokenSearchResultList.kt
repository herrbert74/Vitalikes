package com.zsoltbertalan.vitalikes.ui.tokens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.babestudios.base.compose.simpleVerticalScrollbar
import com.zsoltbertalan.vitalikes.domain.model.TokenBalance

@Composable
fun TokenSearchResultList(
	items: List<TokenBalance>
) {

	val backgroundColor =
		if (items.isEmpty()) colorResource(android.R.color.transparent)
		else colorResource(android.R.color.transparent)

	Box(
		Modifier
			.fillMaxSize(1f)
			.background(backgroundColor)
	) {

		val listState = rememberLazyListState()

		LazyColumn(
			Modifier
				.simpleVerticalScrollbar(listState)
				.background(backgroundColor),
			state = listState
		) {
			items(
				items = items,
				key = { item -> item.hashCode() },
				itemContent = { tokenBalance ->
					TokenBalanceRow(tokenBalance)
					HorizontalDivider()
				})
		}

	}
}