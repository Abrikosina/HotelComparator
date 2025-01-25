package com.example.hotelcomparator.feature.photo.data.model

import com.google.gson.annotations.SerializedName

class PhotoDataResponse(
	@SerializedName("id") val id: String,
	@SerializedName("is_blessed") val isBlessed: Boolean,
	@SerializedName("caption") val caption: String,
	@SerializedName("published_date") val publishedDate: String,
	@SerializedName("images") val images: Map<String, ImageResponse>
)