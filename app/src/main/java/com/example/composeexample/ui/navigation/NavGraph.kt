package com.example.composeexample.ui.navigation

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.composeexample.CoinViewModel
import com.example.composeexample.model.ResponseState
import com.example.composeexample.ui.screen.FavouriteScreen
import com.example.composeexample.ui.screen.PopularScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    state: ResponseState,
    viewModel: CoinViewModel,
    lazyListStatePopular: LazyListState,
    lazyListStateFavourite: LazyListState
) {
    NavHost(navController, startDestination = CoinsRoutes.PopularScreen.route) {
        composable(CoinsRoutes.PopularScreen.route) {
            PopularScreen(state = state, viewModel, lazyListStatePopular)
        }
        composable(CoinsRoutes.FavouriteScreen.route) {
            FavouriteScreen(state = state, viewModel, lazyListStateFavourite)
        }
    }
}