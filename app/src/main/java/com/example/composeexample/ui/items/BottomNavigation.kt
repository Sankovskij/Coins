package com.example.composeexample.ui.items

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.composeexample.ui.navigation.CoinsRoutes

@Composable
fun BottomNavigationMenu(navController: NavController) {
    val items = listOf(
        CoinsRoutes.PopularScreen,
        CoinsRoutes.FavouriteScreen,
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primarySurface
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(item.Icon, contentDescription = null)
                },
                label = {
                    Text(
                        text = stringResource(id = item.titleRes),
                        fontSize = 16.sp
                    )
                },
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}