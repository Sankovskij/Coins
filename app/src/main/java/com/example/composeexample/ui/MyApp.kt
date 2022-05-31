package com.example.composeexample.ui

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.composeexample.CoinViewModel
import com.example.composeexample.R
import com.example.composeexample.ui.items.BottomNavigationMenu
import com.example.composeexample.ui.items.BottomSheetContent
import com.example.composeexample.ui.navigation.NavGraph
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyApp(
    viewModel: CoinViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val sortState = viewModel.sort
    val currentCoins = remember { viewModel.coin }
    val coinsState = remember { viewModel.latestState }.collectAsState()
    val lazyListStatePopular = rememberLazyListState()
    val lazyListStateFavorite = rememberLazyListState()
    LaunchedEffect(key1 = true) {
        viewModel.getLatest()
    }
    ModalBottomSheetLayout(
        sheetContent = {
            BottomSheetContent(sortState) {
                viewModel.changeFilter(it)
                scope.launch {
                    modalBottomSheetState.hide()
                    lazyListStatePopular.scrollToItem(0, 0)
                    lazyListStateFavorite.scrollToItem(0, 0)
                }
            }
        },
        sheetShape = RoundedCornerShape(10.dp),
        sheetState = modalBottomSheetState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "${stringResource(R.string.coin)} ${currentCoins.value}") },
                    actions = {
                        IconButton(onClick = {
                            scope.launch {
                                if (!modalBottomSheetState.isVisible) {
                                    modalBottomSheetState.show()
                                }
                            }
                        }) {
                            Icon(Icons.Filled.KeyboardArrowDown, stringResource(R.string.sort))

                        }
                    }
                )
            },
            bottomBar = {
                BottomNavigationMenu(navController = navController)
            }
        ) {
            NavGraph(navController = navController, state = coinsState.value, viewModel = viewModel, lazyListStatePopular, lazyListStateFavorite)
        }
    }
}