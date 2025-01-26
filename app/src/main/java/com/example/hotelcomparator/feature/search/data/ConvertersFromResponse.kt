package com.example.hotelcomparator.feature.search.data

import android.util.Log
import com.example.hotelcomparator.core.network.NetworkResult
import com.example.hotelcomparator.feature.search.data.model.AddressResponse
import com.example.hotelcomparator.feature.search.data.model.SearchDataResponse
import com.example.hotelcomparator.feature.search.data.model.SearchResponse
import com.example.hotelcomparator.feature.search.domain.model.AddressData
import com.example.hotelcomparator.feature.search.domain.model.Search
import com.example.hotelcomparator.feature.search.domain.model.SearchData

fun SearchResponse.toSearch(): Search {
	return Search(this.data.toSearchData())
}

fun List<SearchDataResponse>.toSearchData(): List<SearchData> {
	return this.map { it.toSearchData() }
}

fun SearchDataResponse.toSearchData(): SearchData {
	Log.i("myTag","Response "+this)
	return SearchData(
		locationId = locationId,
		name = name,
		distance = distance.orEmpty(),
		bearing = bearing.orEmpty(),
		addressData = addressData.toAddressData()
	)
}

fun AddressResponse.toAddressData(): AddressData {
	return AddressData(
		firstStreet = firstStreet,
		secondStreet = secondStreet.orEmpty(),
		city = city,
		state = state.orEmpty(),
		country = country.orEmpty(),
		postalCode = postalCode.orEmpty(),
		address = address
	)
}

fun NetworkResult<SearchResponse>.toSearch(): NetworkResult<Search> {
	return when (this) {
		is NetworkResult.Success<SearchResponse> -> {
			Log.i("myTag","success "+this.data)
			Log.i("myTag","success "+this.data.toSearch())
			NetworkResult.Success(this.data.toSearch())
		}

		is NetworkResult.Error<SearchResponse> -> {
			NetworkResult.Error(this.code, this.message)
		}

		is NetworkResult.Exception -> {
			NetworkResult.Exception(this.e)
		}
	}
}