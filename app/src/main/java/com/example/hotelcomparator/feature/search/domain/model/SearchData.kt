package com.example.hotelcomparator.feature.search.domain.model

import com.example.hotelcomparator.feature.photo.domain.model.PhotoData

data class SearchData(
	val locationId: String,
	val name: String,
	val distance: String,
	val bearing: String,
	val addressData: AddressData
) {
	var images: List<PhotoData> = emptyList()
}
