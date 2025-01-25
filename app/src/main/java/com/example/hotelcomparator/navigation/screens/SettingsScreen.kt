package com.example.hotelcomparator.navigation.screens

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.hotelcomparator.feature.SettingsScreenView

const val SETTINGS_SCREEN_ROUTE = "settings"

fun NavGraphBuilder.settingsScreen() {
    composable(SETTINGS_SCREEN_ROUTE) {
        SettingsScreenView()
    }
}