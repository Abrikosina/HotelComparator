package com.example.hotelcomparator.navigation.screens

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.hotelcomparator.feature.search.SearchScreenView

const val SEARCH_SCREEN_ROUTE = "search"

fun NavGraphBuilder.searchScreen(onClick: ((name: String) -> Unit)? = null) {
	composable(SEARCH_SCREEN_ROUTE) {
		SearchScreenView(onClick)
	}
}

fun NavHostController.navigateToSearchScreen() {
	navigate(SEARCH_SCREEN_ROUTE)
}