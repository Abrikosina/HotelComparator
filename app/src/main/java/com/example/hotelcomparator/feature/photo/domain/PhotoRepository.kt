package com.example.hotelcomparator.feature.photo.domain

import com.example.hotelcomparator.core.network.NetworkResult
import com.example.hotelcomparator.feature.photo.domain.model.Photo

interface PhotoRepository {
	suspend fun getPhoto(locationId: String): NetworkResult<Photo>
}