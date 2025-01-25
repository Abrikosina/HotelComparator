package com.example.hotelcomparator.feature.photo.data

import com.example.hotelcomparator.core.network.NetworkResult
import com.example.hotelcomparator.feature.photo.data.model.PhotoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotoApi {
	@GET("{locationId}/photos")
	suspend fun getPhoto(
		@Path("locationId") locationId: String,
		@Query("language") language: String = "ru",
	) : NetworkResult<PhotoResponse>
}