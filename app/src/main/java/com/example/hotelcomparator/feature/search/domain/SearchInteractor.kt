package com.example.hotelcomparator.feature.search.domain

import com.example.hotelcomparator.core.network.NetworkResult
import com.example.hotelcomparator.feature.search.domain.model.Search
import javax.inject.Inject

class SearchInteractor @Inject
constructor(
	private val searchRepository: SearchRepository
) {
	suspend fun startSearch(query: String): NetworkResult<Search> {
		return searchRepository.search(query)
	}
}