package com.example.hotelcomparator.feature.details.data.model

import com.google.gson.annotations.SerializedName

class SubratingResponse(
	@SerializedName("name") val name: String,
	@SerializedName("localized_name") val localizedName: String,
	@SerializedName("rating_image_url") val ratingImageUrl: String,
	@SerializedName("value") val value: String
)