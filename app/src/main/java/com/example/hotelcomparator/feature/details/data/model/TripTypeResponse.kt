package com.example.hotelcomparator.feature.details.data.model

import com.google.gson.annotations.SerializedName

class TripTypeResponse(
	@SerializedName("name") val name: String,
	@SerializedName("localized_name") val localizedName: String,
	@SerializedName("value") val value: String
)