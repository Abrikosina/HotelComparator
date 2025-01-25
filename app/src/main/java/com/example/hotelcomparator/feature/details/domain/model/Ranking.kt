package com.example.hotelcomparator.feature.details.domain.model

data class Ranking(
	val geoLocationId: String,
	val rankingString: String,
	val geoLocationName: String,
	val rankingOutOf: String,
	val ranking: String
)
