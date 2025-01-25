package com.example.hotelcomparator.feature.search.data

import com.example.hotelcomparator.core.network.NetworkResult
import com.example.hotelcomparator.feature.search.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchApi {
	@GET("search")
	suspend fun search(
		@Query("searchQuery") searchQuery: String,
		@Query("category") category: String = "hotels",
		@Query("language") language: String = "ru",
	) : NetworkResult<SearchResponse>
}