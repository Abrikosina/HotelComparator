package com.example.hotelcomparator.feature.photo.data

import android.util.Log
import com.example.hotelcomparator.core.network.NetworkResult
import com.example.hotelcomparator.feature.photo.domain.PhotoRepository
import com.example.hotelcomparator.feature.photo.domain.model.Photo
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
	private val api: PhotoApi
) : PhotoRepository {
	override suspend fun getPhoto(locationId: String): NetworkResult<Photo> {
		return api.getPhoto(locationId).toPhoto()
	}
}