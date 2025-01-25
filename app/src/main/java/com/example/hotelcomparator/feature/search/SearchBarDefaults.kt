package com.example.hotelcomparator.feature.search

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import com.example.hotelcomparator.R
import kotlinx.coroutines.delay

object SearchBarDefaults {

	@Suppress("detekt.ComplexMethod")
	fun getSearchBarState(
		isClicked: Boolean,
		onSearchClick: (() -> Unit)?,
		isPressed: Boolean,
		isFocused: Boolean,
		isSearching: Boolean,
		isEmpty: Boolean
	): SearchBarState {
		return when {
			isClicked && onSearchClick != null -> {
				SearchBarState.PRESSED
			}
			isEmpty && isFocused.not() && isSearching.not() && isPressed.not() -> {
				SearchBarState.NORMAL
			}
			isPressed && isFocused.not() -> {
				SearchBarState.PRESSED
			}
			isFocused && isEmpty.not() -> {
				SearchBarState.TYPING
			}
			isFocused -> {
				SearchBarState.FOCUSED
			}
			isEmpty.not() && isSearching.not() -> {
				SearchBarState.FILLED
			}
			isEmpty.not() && isSearching -> {
				SearchBarState.LOADING
			}
			else -> {
				SearchBarState.NORMAL
			}
		}
	}

	fun getTrailingIconData(
		state: SearchBarState,
		searchText: String,
		onClearSearch: () -> Unit,
	): TrailingIconData? {
		return when {
			state == SearchBarState.TYPING || state == SearchBarState.FILLED -> {
				val data =
					TrailingIconData(
						R.drawable.ic_24_filled_cancel,
						R.color.addition_gray_500,
						R.color.addition_gray_600)
				data.action = onClearSearch
				data
			}
			state == SearchBarState.PRESSED && searchText.isNotEmpty() -> {
				val data =
					TrailingIconData(
						R.drawable.ic_24_filled_cancel,
						R.color.addition_gray_500,
						R.color.addition_gray_600)
				data.action = onClearSearch
				data
			}
			else -> {
				null
			}
		}
	}

//	@Composable
//	fun textStyle(inputState: InputState): TextStyle {
//		return when (inputState) {
//			InputState.DEFAULT,
//			InputState.ERROR ->
//				Typography.headline18W400.copy(colorResource(R.color.addition_gray_900))
//			InputState.DISABLED -> Typography.headline18W400.copy(colorResource(R.color.gray_600))
//		}
//	}

	@Composable
	fun InteractionSource.collectIsClickedAsState(): State<Boolean> {
		val onClick = remember { mutableStateOf(false) }
		LaunchedEffect(this) {
			var wasPressed = false
			interactions.collect { interaction ->
				when (interaction) {
					is PressInteraction.Press -> {
						wasPressed = true
					}
					is PressInteraction.Release -> {
						if (wasPressed) {
							onClick.value = true
						}
						wasPressed = false
					}
					is PressInteraction.Cancel -> {
						wasPressed = false
					}
				}
				delay(10L)
				onClick.value = false
			}
		}
		return onClick
	}
}