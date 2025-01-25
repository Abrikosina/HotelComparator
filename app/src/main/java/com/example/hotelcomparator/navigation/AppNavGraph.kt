package com.example.hotelcomparator.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.hotelcomparator.navigation.screens.MAIN_SCREEN_ROUTE
import com.example.hotelcomparator.navigation.screens.detailsScreen
import com.example.hotelcomparator.navigation.screens.favouritesScreen
import com.example.hotelcomparator.navigation.screens.mainScreen
import com.example.hotelcomparator.navigation.screens.navigateToDetailsScreen
import com.example.hotelcomparator.navigation.screens.navigateToSearchScreen
import com.example.hotelcomparator.navigation.screens.searchScreen
import com.example.hotelcomparator.navigation.screens.settingsScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = MAIN_SCREEN_ROUTE
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        mainScreen(onSearchClick = { navController.navigateToSearchScreen() })
        favouritesScreen()
        settingsScreen()
        searchScreen(onClick = { navController.navigateToDetailsScreen() })
        detailsScreen()
    }
}
