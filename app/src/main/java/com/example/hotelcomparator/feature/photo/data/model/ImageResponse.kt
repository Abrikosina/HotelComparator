package com.example.hotelcomparator.feature.photo.data.model

import com.google.gson.annotations.SerializedName

class ImageResponse(
	@SerializedName("height") val height: Int,
	@SerializedName("width") val width: Int,
	@SerializedName("url") val url: String
)