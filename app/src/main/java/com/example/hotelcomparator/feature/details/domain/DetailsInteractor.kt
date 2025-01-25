package com.example.hotelcomparator.feature.details.domain

import android.util.Log
import com.example.hotelcomparator.core.network.onSuccess
import com.example.hotelcomparator.feature.details.domain.model.LocationDetails
import com.example.hotelcomparator.feature.photo.domain.GetPhotoUseCase
import kotlinx.coroutines.cancel
import javax.inject.Inject

class DetailsInteractor @Inject
constructor(
	private val detailsRepository: DetailsRepository,
	private val photoUseCase: GetPhotoUseCase,
) {

	//private val scope = CoroutineScope(ioDispatcher+SupervisorJob())

	suspend fun getDetails(locationId: String): LocationDetails? {
		var locationDetails: LocationDetails? = null
		val details = detailsRepository.getDetails(locationId)
		val photoResult = photoUseCase.invoke(locationId)
		photoResult.onSuccess { it.data }

		details.onSuccess { data ->
			photoResult.onSuccess { photo ->
				Log.i("myTag","photo "+photo.data)
				data.images = photo.data
				locationDetails = data
			}
		}
		return locationDetails
	}
}