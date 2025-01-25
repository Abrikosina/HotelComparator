package com.example.hotelcomparator.feature.search.data.model

import com.google.gson.annotations.SerializedName

data class SearchDataResponse(
	@SerializedName("location_id") val locationId: String,
	@SerializedName("name") val name: String,
	@SerializedName("distance") val distance: String? = null,
	@SerializedName("bearing") val bearing: String? = null,
	@SerializedName("address_obj") val addressData: AddressResponse
)
