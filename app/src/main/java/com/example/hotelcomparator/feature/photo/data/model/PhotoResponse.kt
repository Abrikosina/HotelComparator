package com.example.hotelcomparator.feature.photo.data.model

import com.google.gson.annotations.SerializedName

class PhotoResponse(
	@SerializedName("data") val data: List<PhotoDataResponse>,
	@SerializedName("paging") val paging: PagingPhotoResponse,
)