package com.example.hotelcomparator.feature.details.data

import com.example.hotelcomparator.core.network.NetworkResult
import com.example.hotelcomparator.feature.details.data.model.HotelPriceResponse
import com.example.hotelcomparator.feature.details.data.model.LocationDetailsResponse
import com.example.hotelcomparator.feature.details.data.model.RankingResponse
import com.example.hotelcomparator.feature.details.data.model.SubratingResponse
import com.example.hotelcomparator.feature.details.data.model.TripTypeResponse
import com.example.hotelcomparator.feature.details.domain.model.HotelPrice
import com.example.hotelcomparator.feature.details.domain.model.LocationDetails
import com.example.hotelcomparator.feature.details.domain.model.Ranking
import com.example.hotelcomparator.feature.details.domain.model.Subrating
import com.example.hotelcomparator.feature.details.domain.model.TripType
import com.example.hotelcomparator.feature.search.data.toAddressData

fun LocationDetailsResponse.toLocationDetails(): LocationDetails {
	return LocationDetails(
		locationId = locationId,
		name = name,
		webUrl = webUrl,
		addressData = addressData.toAddressData(),
		latitude = latitude,
		longitude = longitude,
		timezone = timezone,
		writeReview = writeReview,
		rating = rating,
		ratingImageUrl = ratingImageUrl,
		numReviews = numReviews,
		photoCount = photoCount,
		priceLevel = priceLevel,
		seeAllPhotos = seeAllPhotos,
		amenities = amenities,
		reviewRatingCount = reviewRatingCount,
		rankingData = rankingData.toRanking(),
		tripTypes = tripTypes.map { it.toTripType() },
		subratings = subratings.mapValues { it.value.toSubrating() },
	)
}

fun RankingResponse.toRanking(): Ranking {
	return Ranking(
		geoLocationId = geoLocationId,
		rankingString = rankingString,
		ranking = ranking,
		rankingOutOf = rankingOutOf,
		geoLocationName = geoLocationName
	)
}

fun TripTypeResponse.toTripType(): TripType {
	return TripType(name, localizedName, value)
}

fun SubratingResponse.toSubrating(): Subrating {
	return Subrating(name, localizedName, ratingImageUrl, value)
}

fun NetworkResult<LocationDetailsResponse>.toLocationDetails(): NetworkResult<LocationDetails> {
	return when (this) {
		is NetworkResult.Success<LocationDetailsResponse> -> {
			NetworkResult.Success(this.data.toLocationDetails())
		}

		is NetworkResult.Error<LocationDetailsResponse> -> {
			NetworkResult.Error(this.code, this.message)
		}

		is NetworkResult.Exception -> {
			NetworkResult.Exception(this.e)
		}
	}
}

fun NetworkResult<List<HotelPriceResponse>>.toHotelPrices(): NetworkResult<List<HotelPrice>> {
	return when (this) {
		is NetworkResult.Success<List<HotelPriceResponse>> -> {
			NetworkResult.Success(this.data.toHotelPrices())
		}

		is NetworkResult.Error<List<HotelPriceResponse>> -> {
			NetworkResult.Error(this.code, this.message)
		}

		is NetworkResult.Exception -> {
			NetworkResult.Exception(this.e)
		}
	}
}

fun List<HotelPriceResponse>.toHotelPrices(): List<HotelPrice> {
	return this.map { it.toHotelPrice() }
}

fun HotelPriceResponse.toHotelPrice(): HotelPrice {
	return HotelPrice(price)
}