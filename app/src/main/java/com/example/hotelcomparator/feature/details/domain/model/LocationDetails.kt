package com.example.hotelcomparator.feature.details.domain.model

import com.example.hotelcomparator.feature.photo.domain.model.Image
import com.example.hotelcomparator.feature.photo.domain.model.PhotoData
import com.example.hotelcomparator.feature.search.domain.model.AddressData

data class LocationDetails(
	val locationId: String,
	val name: String,
	val webUrl: String,
	val addressData: AddressData,
	val latitude: String,
	val longitude: String,
	val timezone: String,
	val writeReview: String,
	val rating: String,
	val ratingImageUrl: String,
	val numReviews: String,
	val photoCount: String,
	val priceLevel: String,
	val seeAllPhotos: String,
	val amenities: List<String>,
	val reviewRatingCount: Map<String, String>,
	val rankingData: Ranking,
	val tripTypes: List<TripType>,
	val subratings: Map<String, Subrating>
) {
	var images: List<PhotoData> = emptyList()
}
