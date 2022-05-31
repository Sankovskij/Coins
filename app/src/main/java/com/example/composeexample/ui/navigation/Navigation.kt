package com.example.composeexample.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.composeexample.R

sealed class CoinsRoutes(val route: String, val titleRes: Int, val Icon: ImageVector) {
    object PopularScreen : CoinsRoutes("popular", R.string.popular, Icons.Filled.Home)
    object FavouriteScreen : CoinsRoutes("favourite", R.string.favourite, Icons.Filled.Star)
}