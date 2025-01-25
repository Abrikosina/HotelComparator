package com.example.hotelcomparator.navigation.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.hotelcomparator.R

sealed class BottomScreen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    data object Main : BottomScreen(MAIN_SCREEN_ROUTE, R.string.main_screen, Icons.Filled.Search)
    data object Favourites :
        BottomScreen(FAVOURITES_SCREEN_ROUTE, R.string.favourites_screen, Icons.Filled.Favorite)

    data object Settings :
        BottomScreen(SETTINGS_SCREEN_ROUTE, R.string.settings_screen, Icons.Filled.Settings)
}