package com.example.hotelcomparator.feature.details.data.model

import com.example.hotelcomparator.feature.search.data.model.AddressResponse
import com.google.gson.annotations.SerializedName

class LocationDetailsResponse(
	@SerializedName("location_id") val locationId: String,
	@SerializedName("name") val name: String,
	@SerializedName("web_url") val webUrl: String,
	@SerializedName("address_obj") val addressData: AddressResponse,
	@SerializedName("latitude") val latitude: String,
	@SerializedName("longitude") val longitude: String,
	@SerializedName("timezone") val timezone: String,
	@SerializedName("write_review") val writeReview: String,
	@SerializedName("rating") val rating: String,
	@SerializedName("rating_image_url") val ratingImageUrl: String,
	@SerializedName("num_reviews") val numReviews: String,
	@SerializedName("photo_count") val photoCount: String,
	@SerializedName("price_level") val priceLevel: String,
	@SerializedName("see_all_photos") val seeAllPhotos: String,
	@SerializedName("amenities") val amenities: List<String>,
	@SerializedName("review_rating_count") val reviewRatingCount: Map<String, String>,
	@SerializedName("ranking_data") val rankingData: RankingResponse,
	@SerializedName("trip_types") val tripTypes: List<TripTypeResponse>,
	@SerializedName("subratings") val subratings: Map<String, SubratingResponse>,
)