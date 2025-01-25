package com.example.hotelcomparator.feature.search.data

import com.example.hotelcomparator.core.network.NetworkResult
import com.example.hotelcomparator.feature.search.domain.SearchRepository
import com.example.hotelcomparator.feature.search.domain.model.Search
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
	private val api: SearchApi
) : SearchRepository {
	override suspend fun search(query: String): NetworkResult<Search> = api.search(query).toSearch()
}