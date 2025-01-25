package com.example.hotelcomparator.feature

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SettingsScreenView() {
    Box {
        Text(text = "Settings Screen", modifier = Modifier.align(Alignment.Center))
    }
}