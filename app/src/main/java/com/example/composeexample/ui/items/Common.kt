package com.example.composeexample.ui.items

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.composeexample.R
import com.example.composeexample.model.OneCoin
import com.example.composeexample.model.ResponseState

@Composable
fun StateNavigation(
    state: ResponseState,
    lazyListStateFavourite: LazyListState,
    isFavourite: Boolean,
    itemListComposable: @Composable (item: OneCoin) -> Unit
) {
    when (state) {
        is ResponseState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is ResponseState.Data -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                state = lazyListStateFavourite,
                contentPadding = PaddingValues(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 50.dp)
            ) {
                items(
                    if (isFavourite) state.list.filter { it.isFavourite } else state.list
                ) { item ->
                    itemListComposable(item)
                }
            }
        }
        is ResponseState.Error -> {
            Column {
                Text(text = stringResource(R.string.error))
                state.e.localizedMessage?.let { it1 ->
                    Text(
                        text = it1
                    )
                }
            }
        }
    }
}