package com.example.hotelcomparator.feature.search.domain

import com.example.hotelcomparator.core.network.NetworkResult
import com.example.hotelcomparator.feature.search.domain.model.Search

interface SearchRepository {
	suspend fun search(query: String): NetworkResult<Search>
}