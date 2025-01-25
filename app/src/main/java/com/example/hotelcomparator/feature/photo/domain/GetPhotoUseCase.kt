package com.example.hotelcomparator.feature.photo.domain

import com.example.hotelcomparator.core.network.NetworkResult
import com.example.hotelcomparator.feature.photo.domain.model.Photo
import javax.inject.Inject

class GetPhotoUseCase @Inject constructor(private val repository: PhotoRepository) {
	suspend fun invoke(locationId: String): NetworkResult<Photo> = repository.getPhoto(locationId)
}