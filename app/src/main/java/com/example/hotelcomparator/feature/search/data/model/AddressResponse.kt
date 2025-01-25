package com.example.hotelcomparator.feature.search.data.model

import com.google.gson.annotations.SerializedName

data class AddressResponse(
	@SerializedName("street1") val firstStreet: String,
	@SerializedName("street2") val secondStreet: String?,
	@SerializedName("city") val city: String,
	@SerializedName("state") val state: String?,
	@SerializedName("country") val country: String,
	@SerializedName("postalcode") val postalCode: String?,
	@SerializedName("address_string") val address: String,
)
