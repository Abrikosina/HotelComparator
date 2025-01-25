package com.example.hotelcomparator.feature.details.data

import com.example.hotelcomparator.core.network.NetworkResult
import com.example.hotelcomparator.feature.details.data.model.LocationDetailsResponse
import com.example.hotelcomparator.feature.search.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailsApi {

	@GET("{locationId}/details")
	suspend fun getLocationDetails(
		@Path("locationId") locationId: String,
		@Query("language") language: String = "ru",
	): NetworkResult<LocationDetailsResponse>
}