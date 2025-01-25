package com.example.hotelcomparator.feature.details.domain

import com.example.hotelcomparator.core.network.NetworkResult
import com.example.hotelcomparator.feature.details.domain.model.LocationDetails

interface DetailsRepository {
	suspend fun getDetails(locationId: String): NetworkResult<LocationDetails>
}