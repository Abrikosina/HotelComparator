package com.example.hotelcomparator.feature.details.presentation

import androidx.compose.runtime.Immutable
import com.example.hotelcomparator.feature.details.domain.model.LocationDetails
import com.example.hotelcomparator.feature.details.presentation.model.WebSiteItem

@Immutable
data class DetailsScreenState(
	val details: LocationDetails? = null,
	val currentPage: Int = 1,
	val currentImageUrl: String = "",
	val isShowDatePicker: Boolean = false,
	val webSiteItems: List<WebSiteItem> = emptyList()
)
