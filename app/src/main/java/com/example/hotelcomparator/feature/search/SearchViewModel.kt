package com.example.hotelcomparator.feature.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelcomparator.core.network.NetworkResult
import com.example.hotelcomparator.core.network.onError
import com.example.hotelcomparator.core.network.onException
import com.example.hotelcomparator.core.network.onSuccess
import com.example.hotelcomparator.feature.photo.domain.GetPhotoUseCase
import com.example.hotelcomparator.feature.photo.domain.model.Photo
import com.example.hotelcomparator.feature.search.di.IoDispatcher
import com.example.hotelcomparator.feature.search.domain.SearchInteractor
import com.example.hotelcomparator.feature.search.domain.model.Search
import com.example.hotelcomparator.feature.search.presentation.SideEffects
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject
constructor(
	@IoDispatcher private val ioDispatcher: CoroutineDispatcher,
	private val searchInteractor: SearchInteractor,
	private val photoUseCase: GetPhotoUseCase
) : ViewModel() {
	private val _screenState = MutableStateFlow(SearchScreenState())
	val screenState: StateFlow<SearchScreenState> = _screenState

	private val _sideEffects = Channel<SideEffects>()
	val sideEffects = _sideEffects.receiveAsFlow()

	private var job: Job? = null

	private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
		viewModelScope.launch { _sideEffects.send(SideEffects.ExceptionEffect(throwable)) }
	}

	fun onClearSearch() {
		_screenState.update { state ->
			state.copy(searchQuery = "", locations = emptyList())
		}
	}

	fun onSearchTextChange(searchQuery: String) {
		if (searchQuery == screenState.value.searchQuery) return
		_screenState.update { state ->
			state.copy(isSearching = true, searchQuery = searchQuery)
		}
		Log.i("myTag","KEKE "+screenState.value.isSearching)
		job?.cancel()
		job =
			viewModelScope.launch(exceptionHandler) {
					delay(DELAY)
					Log.i("myTag","beforeREsult ")
					val result = startSearch(searchQuery)
					result.onSuccess { successResult ->
							Log.i("myTag","lllll")
						val pep = mutableMapOf<String, Deferred<NetworkResult<Photo>>>()
						successResult.data.forEach { searchData ->
							val kek: Deferred<NetworkResult<Photo>> = async { photoUseCase.invoke(searchData.locationId) }
							pep[searchData.locationId] = kek
						}
						val newResult = successResult.data.map { searchData ->
							if (pep.keys.find { it == searchData.locationId } != null) {
								Log.i("myTag","Enter")
								val photoResult: NetworkResult<Photo>? = pep[searchData.locationId]?.await()
								photoResult?.onSuccess {
									searchData.images = it.data//.firstOrNull()?.images.orEmpty()
								}
								searchData
							} else {
								Log.i("myTag","not Enter")
								searchData
							}
						}
						_screenState.update { state ->
								state.copy(
									locations = newResult
								)
							}
						}
						.onError { _, message ->
							_sideEffects.send(SideEffects.ErrorEffect(message.orEmpty()))
						}
						.onException { throwable ->
							_sideEffects.send(SideEffects.ExceptionEffect(throwable))
						}
				Log.i("myTag","KEKE2 "+screenState.value.isSearching)
				_screenState.update { state -> state.copy(isSearching = false) }
				Log.i("myTag","KEKE3 "+screenState.value.isSearching)
			}
	}

	private suspend fun startSearch(query: String): NetworkResult<Search> {
		return searchInteractor.startSearch(query)
	}

	companion object {
		private const val DELAY = 500L
	}
}