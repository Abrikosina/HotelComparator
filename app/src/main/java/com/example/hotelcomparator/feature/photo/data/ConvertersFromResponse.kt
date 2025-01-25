package com.example.hotelcomparator.feature.photo.data

import com.example.hotelcomparator.core.network.NetworkResult
import com.example.hotelcomparator.feature.photo.data.model.ImageResponse
import com.example.hotelcomparator.feature.photo.data.model.PhotoDataResponse
import com.example.hotelcomparator.feature.photo.data.model.PhotoResponse
import com.example.hotelcomparator.feature.photo.domain.model.Image
import com.example.hotelcomparator.feature.photo.domain.model.Photo
import com.example.hotelcomparator.feature.photo.domain.model.PhotoData

fun PhotoResponse.toPhoto(): Photo {
	return Photo(data = data.map { it.toPhotoData() })
}

fun PhotoDataResponse.toPhotoData(): PhotoData {
	return PhotoData(id = id, images = images.mapValues { it.value.toImage() })
}

fun ImageResponse.toImage(): Image {
	return Image(height = height, width = width, url = url)
}

fun NetworkResult<PhotoResponse>.toPhoto(): NetworkResult<Photo> {
	return when (this) {
		is NetworkResult.Success<PhotoResponse> -> {
			NetworkResult.Success(this.data.toPhoto())
		}

		is NetworkResult.Error<PhotoResponse> -> {
			NetworkResult.Error(this.code, this.message)
		}

		is NetworkResult.Exception -> {
			NetworkResult.Exception(this.e)
		}
	}
}