package com.example.hotelcomparator.feature.main.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
//    private val repository: RemoteProductsRepository,
//    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _screenState = MutableStateFlow(MainScreenState(date = getCurrentDate()))
    val screenState: StateFlow<MainScreenState> = _screenState

    init {

    }

    private fun initializeInitialState() {

    }

    private fun getCurrentDate(): String {
        val formatter = DateTimeFormatter.ofPattern(DATE_PATTERN)
        val currentDate = LocalDate.now()
        val currentFormattedDate = currentDate.format(formatter)
        val nextFormattedDate = currentDate.plusDays(ONE_DAY).format(formatter)
        val date = "$currentFormattedDate - $nextFormattedDate"
        Log.i("myTag","currentDate "+date)
        return date
    }

    private fun getCurrentLocation() {

    }

    fun onStartSearch(query: String = "") {

    }

    companion object {
        private const val DATE_PATTERN = "MMMM dd"
        private const val ONE_DAY = 1L
    }
}