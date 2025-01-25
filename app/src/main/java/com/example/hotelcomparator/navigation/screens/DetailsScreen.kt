package com.example.hotelcomparator.navigation.screens

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.hotelcomparator.feature.details.presentation.DetailsScreenView

const val DETAILS_SCREEN_ROUTE = "details"

fun NavGraphBuilder.detailsScreen(onClick: (() -> Unit)? = null) {
	composable(DETAILS_SCREEN_ROUTE) {
		DetailsScreenView()
	}
}

fun NavHostController.navigateToDetailsScreen() {
	navigate(DETAILS_SCREEN_ROUTE)
}