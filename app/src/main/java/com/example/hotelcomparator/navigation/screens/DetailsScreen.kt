package com.example.hotelcomparator.navigation.screens

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.hotelcomparator.feature.details.presentation.DetailsScreenView

private const val NAME_KEY = "name"
const val DETAILS_SCREEN_BASIC_ROUTE = "details"
const val DETAILS_SCREEN_ROUTE = "$DETAILS_SCREEN_BASIC_ROUTE/{$NAME_KEY}"

fun NavGraphBuilder.detailsScreen(onClick: (() -> Unit)? = null) {
	composable(DETAILS_SCREEN_ROUTE,
		arguments = listOf(
		navArgument(NAME_KEY) { type = NavType.StringType },
	)) { backStackEntry ->
		val name = backStackEntry.arguments?.getString(NAME_KEY).orEmpty()
		DetailsScreenView(name)
	}
}

fun NavHostController.navigateToDetailsScreen(name: String) {
	navigate(route = "$DETAILS_SCREEN_BASIC_ROUTE/$name")
}