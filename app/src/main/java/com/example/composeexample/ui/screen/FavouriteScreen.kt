package com.example.composeexample.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composeexample.CoinViewModel
import com.example.composeexample.model.OneCoin
import com.example.composeexample.model.ResponseState
import com.example.composeexample.ui.items.StateNavigation

@Composable
fun FavouriteScreen(
    state: ResponseState,
    viewModel: CoinViewModel,
    lazyListStateFavourite: LazyListState
) {
    StateNavigation(
        state,
        lazyListStateFavourite,
        true
    ) { item -> FavouriteCoinItem(item, viewModel) }
}

@Composable
fun FavouriteCoinItem(item: OneCoin, viewModel: CoinViewModel) {
    val isFavourite = remember { mutableStateOf(item.isFavourite) }
    isFavourite.value = item.isFavourite
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Text(text = item.name)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = item.value.toString())
        }
        IconToggleButton(
            checked = isFavourite.value,
            onCheckedChange = {
                if (isFavourite.value) {
                    viewModel.deleteFromFavourite(item.name)
                } else {
                    viewModel.addToFavourite(item.name)
                }
                isFavourite.value = !isFavourite.value
                item.isFavourite = !item.isFavourite
            }
        ) {
            Icon(
                imageVector = if (isFavourite.value) {
                    Icons.Filled.Star
                } else {
                    Icons.TwoTone.Star
                },
                contentDescription = null
            )
        }
    }
}