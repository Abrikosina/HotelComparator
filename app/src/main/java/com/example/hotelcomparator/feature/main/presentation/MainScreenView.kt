package com.example.hotelcomparator.feature.main.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.hotelcomparator.CommonSearchView

@Composable
fun MainScreenView(onSearchClick: () -> Unit, viewModel: MainViewModel = hiltViewModel()) {
    val state by viewModel.screenState.collectAsState()

    Box {
        Text(text = "Main Screen", modifier = Modifier.align(Alignment.Center))
        CommonSearchView(
            dateText = state.date,
            guestsText = state.guests,
            destination = state.destination,
            onSearchClick = onSearchClick)
    }
}