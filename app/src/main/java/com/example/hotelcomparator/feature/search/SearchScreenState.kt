package com.example.hotelcomparator.feature.search

import com.example.hotelcomparator.feature.search.domain.model.SearchData

data class SearchScreenState(
	val locations: List<SearchData> = emptyList(),
	val searchQuery: String = "",
	val isSearching: Boolean = false
)
