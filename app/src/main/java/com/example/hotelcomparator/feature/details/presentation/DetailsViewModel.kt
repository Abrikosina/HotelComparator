package com.example.hotelcomparator.feature.details.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelcomparator.feature.details.domain.DetailsInteractor
import com.example.hotelcomparator.feature.details.domain.PricesRepository
import com.example.hotelcomparator.feature.details.presentation.handlers.OneHundredHtmlHandler
import com.example.hotelcomparator.feature.details.presentation.handlers.OstrovokHtmlHandler
import com.example.hotelcomparator.feature.details.presentation.model.WebSiteItem
import com.example.hotelcomparator.feature.fetcher.HtmlExtractionService
import com.example.hotelcomparator.feature.search.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject
constructor(
	private val detailsInteractor: DetailsInteractor,
	private val pricesRepository: PricesRepository,
	@IoDispatcher private val ioDispatcher: CoroutineDispatcher,
	private val service: HtmlExtractionService,
	private val ostrovokHtmlHandler: OstrovokHtmlHandler,
	private val oneHundredHtmlHandler: OneHundredHtmlHandler
) : ViewModel() {

	private val _screenState = MutableStateFlow(DetailsScreenState())
	val screenState: StateFlow<DetailsScreenState> = _screenState

	init {
		getDetails()
		getWebSiteItems()
		observeHotelPrices()
	}

	fun onStart(name: String) {
		Log.i("myTag","NAME "+name)
	}

	private fun getDetails() {
		viewModelScope.launch(ioDispatcher) {
			val details = detailsInteractor.getDetails("17826397")
			val images = details?.images ?: emptyList()
			val currentImageUrl =
				if (images.isEmpty()) {
					""
				} else {
					images[_screenState.value.currentPage].images["original"]?.url.orEmpty()
				}
			_screenState.update { state ->
				state.copy(details = details, currentImageUrl = currentImageUrl)
			}
		}
	}

	private fun getWebSiteItems() {
		val ostrovokUrl =
			"https://ostrovok.ru/hotel/russia/st._petersburg/mid9115605/valo_hotel_city/?q=2042&dates=30.01.2025-31.01.2025&guests=2&residency=ru-ru&sid=5e33ca7e-b60f-4be4-b696-9edd5980ab83"
		val oneHundredUrl =
			"https://m.101hotels.com/main/cities/sankt-peterburg/valo_hotel_city.html?in=30.01.2025&out=31.01.2025&adults=2"
		val items = listOf(
			WebSiteItem("Ostrovok", true, ostrovokUrl, ostrovokHtmlHandler),
			WebSiteItem("OneHundred", true, oneHundredUrl, oneHundredHtmlHandler)
		)
		_screenState.update { state ->
			state.copy(webSiteItems = items)
		}
	}

	fun onChangeDate(startDate: Long?, endDate: Long?) {
		Log.i("myTag", "startDate " + startDate)
		Log.i("myTag", "endDate " + endDate)
		_screenState.update { state ->
			state.copy(isShowDatePicker = false)
		}
//		viewModelScope.launch(ioDispatcher) {
//			val result = pricesRepository.getPrices("valo_hotel_city", "26.12.2024", "28.12.2024")
//			result.onSuccess {
//				Log.i("myTag","success "+it)
//			}.onError{ _, _ ->
//				Log.i("myTag","errror ")
//			}.onException {
//				Log.i("myTag","exception "+it)
//			}
//		}
	}

	@OptIn(ExperimentalCoroutinesApi::class)
	fun observeHotelPrices() {
		viewModelScope.launch {
			ostrovokHtmlHandler.observePrice().zip(oneHundredHtmlHandler.observePrice()) { ostrovok, oneHundred ->
				ostrovok to oneHundred
			}.collect { commonPair ->
				Log.i("myTag","PLO "+commonPair)
				val newItems: List<WebSiteItem> = screenState.value.webSiteItems.map { item ->
					Log.i("myTag","Enter "+item)
					return@map when {
						item.name == "Ostrovok" -> item.copy(
							price = commonPair.first.second,
							isLoading = false
						)

						item.name == "OneHundred" -> {
							Log.i("myTag","FFF "+commonPair.second.second)
							item.copy(
								price = commonPair.second.second,
								isLoading = false
							)
						}

						else -> item
					}
				}

				newItems.forEach{
					Log.i("myTag","item "+it)
				}
				_screenState.update { state ->
					state.copy(webSiteItems = newItems)
				}
			}
		}
	}

	fun showDatePicker(isShow: Boolean) {
		_screenState.update { state ->
			state.copy(isShowDatePicker = isShow)
		}
	}

	fun onChangePage(currentPage: Int) {
		Log.i("myTag", "onChangePage " + currentPage)
		Log.i("myTag", "onChangePage2 " + _screenState.value.currentPage)
		if (currentPage == 0 && _screenState.value.currentPage == 1) return
		Log.i("myTag", "onChangePage3 ")
		val isSwipeLeftDirection = _screenState.value.currentPage - 1 > currentPage
		val newPage = if (isSwipeLeftDirection) {
			_screenState.value.currentPage - 1
		} else {
			_screenState.value.currentPage + 1
		}
		_screenState.value.details?.images?.forEach {
			Log.i("myTag", "ENTER " + it)
		}
		val imageUrl =
			_screenState.value.details?.images?.get(newPage)?.images?.get("original")?.url.orEmpty()
		_screenState.update { state ->
			state.copy(currentImageUrl = imageUrl, currentPage = newPage)
		}
	}
}