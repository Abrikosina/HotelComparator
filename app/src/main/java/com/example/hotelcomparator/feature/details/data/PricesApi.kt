package com.example.hotelcomparator.feature.details.data

import com.example.hotelcomparator.core.network.NetworkResult
import com.example.hotelcomparator.feature.details.data.model.HotelPriceResponse
import com.example.hotelcomparator.feature.details.data.model.LocationDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PricesApi {

	@GET
	suspend fun getPrices(
		@Url url: String,
		@Query("hotelName") hotelName: String,
		@Query("startDate") startDate: String,
		@Query("endDate") endDate: String
	): NetworkResult<List<HotelPriceResponse>>
}