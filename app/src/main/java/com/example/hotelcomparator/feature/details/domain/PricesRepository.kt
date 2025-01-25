package com.example.hotelcomparator.feature.details.domain

import com.example.hotelcomparator.core.network.NetworkResult
import com.example.hotelcomparator.feature.details.domain.model.HotelPrice

interface PricesRepository {
	suspend fun getPrices(
		hotelName: String,
		startDate: String,
		endDate: String
	): NetworkResult<List<HotelPrice>>
}