package com.example.hotelcomparator.feature.search.presentation

sealed class SideEffects  {
	data class ErrorEffect(val err: String): SideEffects()
	data class ExceptionEffect(val throwable: Throwable): SideEffects()
}