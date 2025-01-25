package com.example.hotelcomparator.navigation.screens

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.hotelcomparator.feature.main.presentation.MainScreenView
import com.example.hotelcomparator.feature.main.presentation.MainViewModel

const val MAIN_SCREEN_ROUTE = "main"

fun NavGraphBuilder.mainScreen(onSearchClick: () -> Unit) {
    composable(MAIN_SCREEN_ROUTE) {
        MainScreenView(onSearchClick)
    }
}