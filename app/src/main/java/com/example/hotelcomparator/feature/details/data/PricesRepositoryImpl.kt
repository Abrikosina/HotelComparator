package com.example.hotelcomparator.feature.details.data

import com.example.hotelcomparator.core.network.NetworkResult
import com.example.hotelcomparator.feature.details.domain.PricesRepository
import com.example.hotelcomparator.feature.details.domain.model.HotelPrice
import javax.inject.Inject

class PricesRepositoryImpl @Inject constructor(private val pricesApi: PricesApi) : PricesRepository {

	override suspend fun getPrices(
		hotelName: String,
		startDate: String,
		endDate: String
	): NetworkResult<List<HotelPrice>> {
		return pricesApi.getPrices(URL, hotelName, startDate, endDate).toHotelPrices()
	}

	companion object {
		private const val URL = "http://192.168.0.100:8080/prices"
	}
}