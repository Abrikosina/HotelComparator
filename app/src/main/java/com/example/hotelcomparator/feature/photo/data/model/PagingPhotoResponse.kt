package com.example.hotelcomparator.feature.photo.data.model

import com.google.gson.annotations.SerializedName

class PagingPhotoResponse(
	@SerializedName("next") val next: String?,
	@SerializedName("previous") val previous: String?,
	@SerializedName("results") val result: Int?,
	@SerializedName("total_results") val totalResult: Int?,
	@SerializedName("skipped") val skippedCount: Int?,
)