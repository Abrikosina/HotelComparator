package com.example.hotelcomparator.feature.details.data

import com.example.hotelcomparator.core.network.NetworkResult
import com.example.hotelcomparator.feature.details.domain.DetailsRepository
import com.example.hotelcomparator.feature.details.domain.model.LocationDetails
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
	private val api: DetailsApi
) : DetailsRepository {
	override suspend fun getDetails(locationId: String): NetworkResult<LocationDetails> {
		return api.getLocationDetails(locationId).toLocationDetails()
	}
}