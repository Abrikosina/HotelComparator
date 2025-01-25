package com.example.hotelcomparator.feature.details.data.model

import com.google.gson.annotations.SerializedName

class RankingResponse(
	@SerializedName("geo_location_id") val geoLocationId: String,
	@SerializedName("ranking_string") val rankingString: String,
	@SerializedName("geo_location_name") val geoLocationName: String,
	@SerializedName("ranking_out_of") val rankingOutOf: String,
	@SerializedName("ranking") val ranking: String
)