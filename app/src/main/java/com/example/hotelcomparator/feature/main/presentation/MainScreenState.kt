package com.example.hotelcomparator.feature.main.presentation

import java.time.LocalDateTime

data class MainScreenState(
	val date: String = "",
	val guests: String = "1 гость",
	val destination: String = "Valo",
	val items: List<UiItem> = emptyList(),
	val guestsCount: Int = 1,
	val chosenDate: LocalDateTime? = null
)