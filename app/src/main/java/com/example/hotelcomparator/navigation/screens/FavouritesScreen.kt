package com.example.hotelcomparator.navigation.screens

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.hotelcomparator.feature.FavouritesScreenView

const val FAVOURITES_SCREEN_ROUTE = "favourites"

fun NavGraphBuilder.favouritesScreen() {
    composable(FAVOURITES_SCREEN_ROUTE) {
        FavouritesScreenView()
    }
}