package com.example.hotelcomparator.feature.search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.hotelcomparator.R
import com.example.hotelcomparator.feature.search.SearchBarDefaults.collectIsClickedAsState
import com.example.hotelcomparator.feature.search.SearchBarDefaults.getSearchBarState
import com.example.hotelcomparator.feature.search.SearchBarDefaults.getTrailingIconData
import com.example.hotelcomparator.feature.search.domain.model.SearchData

@Composable
fun SearchScreenView(onSearchClick: ((name: String) -> Unit)?, viewModel: SearchViewModel = hiltViewModel()) {
	val lifecycleOwner = LocalLifecycleOwner.current
	val state by viewModel.screenState.collectAsStateWithLifecycle(lifecycleOwner = lifecycleOwner)
	Column {
		SearchBar(
			searchText = state.searchQuery,
			placeholderText = "Введите запрос",
			isSearching = state.isSearching,
			onValueChange = { textField ->
				viewModel.onSearchTextChange(textField)
			},
			onStartSearch = {},
			onClearSearch = { viewModel.onClearSearch() },
			onSearchClick = null,
			isShowKeyboard = true,
		)

		SearchItems(
			state.locations,
			onSearchClick = onSearchClick,
			modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
		)
	}
}

@Composable
fun SearchItems(
	searchDataItems: List<SearchData>,
	onSearchClick: ((name: String) -> Unit)?,
	modifier: Modifier = Modifier
) {
	Log.i("myTag", "SearchItems")
	LazyColumn(modifier = modifier) {
		items(searchDataItems, key = { item -> item.locationId }) { item ->
			SearchItem(item, onSearchClick)
		}
	}
}

@Composable
fun SearchItem(item: SearchData, onSearchClick: ((name: String) -> Unit)?, modifier: Modifier = Modifier) {
	Log.i("myTag", "SearchItem")
	Row(
		modifier = Modifier
			.clickable(onClick = { onSearchClick?.invoke(item.name) })
			.fillMaxWidth()
			.padding(vertical = 8.dp)
	) {
		Box(
			modifier = Modifier
				.size(64.dp)
				.clip(RoundedCornerShape(16.dp))
				.background(Color.Gray, RoundedCornerShape(16.dp))
		) {
			item.images.forEach {
				Log.i("myTag", "keke " + it)
			}
			val image = item.images.firstOrNull()?.images?.entries?.find { it.key == "small" }?.value?.url
			if (image.isNullOrEmpty()) {
				Icon(
					painter = painterResource(R.drawable.ic_location_on_24),
					contentDescription = null,
					modifier = Modifier.align(
						Alignment.Center
					)
				)
			} else {
				AsyncImage(
					model = image,
					contentDescription = null,
					//placeholder = painterResource(R.drawable.ic_location_on_24),
					modifier = Modifier
						.size(64.dp)
						.align(Alignment.Center)
						.clip(RoundedCornerShape(16.dp))
						.background(Color.Gray, RoundedCornerShape(16.dp))
				)
			}
		}
		Column(
			modifier = Modifier
				.align(Alignment.CenterVertically)
				.padding(horizontal = 12.dp)
		) {
			Text(text = item.name)
			Text(text = item.addressData.city, modifier = Modifier.padding(top = 2.dp))
		}
	}
}

@Composable
fun SearchBar(
	searchText: String,
	placeholderText: String,
	isSearching: Boolean,
	onValueChange: (String) -> Unit,
	onStartSearch: () -> Unit,
	onClearSearch: () -> Unit,
	onSearchClick: (() -> Unit)?,
	modifier: Modifier = Modifier,
	isShowKeyboard: Boolean = false,
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
	val isFocused = interactionSource.collectIsFocusedAsState()
	val isPressed = interactionSource.collectIsPressedAsState()
	val isClicked = interactionSource.collectIsClickedAsState()

	val focusRequester = remember { FocusRequester() }
	val focusManager = LocalFocusManager.current
	val keyboardController = LocalSoftwareKeyboardController.current

	val state =
		getSearchBarState(
			isClicked.value,
			onSearchClick,
			isPressed.value,
			isFocused.value,
			isSearching,
			searchText.isBlank()
		)
	Log.i("myTag", "state " + state)

	val trailingIconData =
		getTrailingIconData(state, searchText, onClearSearch)

	val backgroundColor =
		if (state == SearchBarState.PRESSED) {
			R.color.addition_gray_200
		} else {
			R.color.addition_gray_100
		}

	val newModifier =
		if (onSearchClick != null) {
			Modifier.clickable(
				indication = null,
				onClick = onSearchClick,
				enabled = true,
				interactionSource = interactionSource
			)
		} else {
			modifier
		}

	LaunchedEffect(key1 = isShowKeyboard) {
		if (isShowKeyboard) {
			focusRequester.requestFocus()
			keyboardController?.show()
		} else {
			focusManager.clearFocus()
			keyboardController?.hide()
		}
	}

	SearchBar(
		searchText = searchText,
		placeholderText = placeholderText,
		isSearching = isSearching,
		onValueChange = onValueChange,
		onSearchClick = onSearchClick,
		onStartSearch = onStartSearch,
		trailingIconData = trailingIconData,
		backgroundColor = backgroundColor,
		interactionSource = interactionSource,
		focusRequester = focusRequester,
		state = state,
		modifier = newModifier
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
	searchText: String,
	placeholderText: String,
	isSearching: Boolean,
	onValueChange: (String) -> Unit,
	onStartSearch: () -> Unit,
	onSearchClick: (() -> Unit)?,
	backgroundColor: Int,
	trailingIconData: TrailingIconData?,
	interactionSource: MutableInteractionSource,
	focusRequester: FocusRequester,
	state: SearchBarState,
	modifier: Modifier = Modifier,
	visualTransformation: VisualTransformation = VisualTransformation.None,
) {
	BasicTextField(
		value = searchText,
		enabled = onSearchClick == null,
		onValueChange = onValueChange,
		interactionSource = interactionSource,
		singleLine = true,
		keyboardActions = KeyboardActions(onSearch = { onStartSearch.invoke() }),
		keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
		visualTransformation = visualTransformation,
		modifier =
		Modifier
			.focusRequester(focusRequester)
			.indicatorLine(
				enabled = false,
				isError = false,
				colors =
				TextFieldDefaults.colors(
					disabledIndicatorColor = Color.Transparent,
					focusedIndicatorColor = Color.Transparent,
					unfocusedIndicatorColor = Color.Transparent
				),
				interactionSource = interactionSource,
				focusedIndicatorLineThickness = 0.dp,
				unfocusedIndicatorLineThickness = 0.dp
			)
	) { innerTextField ->
		SearchBarContainer(
			searchText = searchText,
			placeholderText = placeholderText,
			isSearching = isSearching,
			onSearchClick = onSearchClick,
			state = state,
			backgroundColor = backgroundColor,
			trailingIconData = trailingIconData,
			innerTextField = innerTextField,
			modifier = modifier,
		)
	}
}

@Composable
fun SearchBarContainer(
	searchText: String,
	placeholderText: String,
	isSearching: Boolean,
	backgroundColor: Int,
	trailingIconData: TrailingIconData?,
	onSearchClick: (() -> Unit)?,
	state: SearchBarState,
	modifier: Modifier = Modifier,
	innerTextField: @Composable (() -> Unit)? = null,
) {
	Row(
		modifier =
		modifier.then(
			Modifier
				.height(40.dp)
				.background(colorResource(id = backgroundColor), RoundedCornerShape(10.dp))
				.clip(RoundedCornerShape(10.dp))
				.padding(8.dp)
		),
		verticalAlignment = Alignment.CenterVertically
	) {
		LeadingIcon()
		Box(
			modifier = Modifier
				.weight(1f)
				.padding(horizontal = 4.dp)
		) {
			if (searchText.isBlank()) {
				Text(
					text = placeholderText,
//					style =
//					Typography.headline18W400.copy(
//						color = colorResource(id = R.color.addition_gray_500)
//					),
					maxLines = 1
				)
			}
			if (onSearchClick != null ||
				state == SearchBarState.NORMAL ||
				state == SearchBarState.FILLED
			) {
				Text(
					text = searchText,
					//style = Typography.headline18W400,
					overflow = TextOverflow.Ellipsis,
					maxLines = 1
				)
			} else {
				innerTextField?.invoke()
			}
		}
		TrailingIcon(isSearching, trailingIconData)
	}
}

@Composable
fun LeadingIcon(modifier: Modifier = Modifier) {
	Icon(
		imageVector = ImageVector.vectorResource(id = R.drawable.ic_16_stroke_search),
		contentDescription = "Search",
		modifier = modifier.then(
			Modifier
				.size(24.dp)
				.padding(4.dp)
		)
	)
}

@Composable
fun TrailingIcon(
	isSearching: Boolean,
	trailingIconData: TrailingIconData?,
	modifier: Modifier = Modifier,
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
	Log.i("myTag", "isSearching " + isSearching)
	val isPressed = interactionSource.collectIsPressedAsState()

	if (isSearching) {
		CircularProgressIndicator(
			color = colorResource(id = R.color.addition_gray_500),
			strokeWidth = 3.dp,
			trackColor = colorResource(R.color.progress_track_black_16),
			modifier = modifier.then(Modifier.size(20.dp))
		)
	} else {
		trailingIconData?.let { data ->
			val iconColor =
				when (isPressed.value) {
					true -> {
						trailingIconData.pressedIconColor ?: trailingIconData.iconColor
					}

					else -> {
						trailingIconData.iconColor
					}
				}
			Image(
				imageVector = ImageVector.vectorResource(id = data.iconRes),
				contentDescription = "Clear",
				modifier =
				modifier.then(
					data.action?.let { action ->
						Modifier
							.clickable(
								indication = null,
								onClick = action,
								enabled = true,
								interactionSource = interactionSource
							)
					} ?: Modifier),
				colorFilter = ColorFilter.tint(colorResource(iconColor)))
		}
	}
}

@Preview
@Composable
fun SunlightSearchBarPreview() {
	Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
		SearchBar(
			searchText = "searchText",
			placeholderText = "PlaceHolder1",
			isSearching = false,
			onValueChange = {},
			onStartSearch = {},
			onClearSearch = {},
			onSearchClick = null,
		)
		SearchBar(
			searchText = "",
			placeholderText = "PlaceHolder2",
			isSearching = false,
			onValueChange = {},
			onStartSearch = {},
			onClearSearch = {},
			onSearchClick = {},
		)
	}
}


//@Composable
//fun SearchInputField(
//	searchText: String,
//	isSearching: Boolean,
//	onQueryChange: (String) -> Unit,
//	onStartSearch: () -> Unit,
//	onClearSearch: () -> Unit,
//	modifier: Modifier = Modifier,
//	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
//) {
//	TextField(
//		value = searchText,
//		onValueChange = onQueryChange,
//		modifier = modifier.then(Modifier.fillMaxWidth()),
//		textStyle = TextStyle(color = Color.Black, fontSize = 14.sp),
//		singleLine = true,
//		interactionSource = interactionSource,
//		colors = TextFieldDefaults.colors(
//			focusedContainerColor = Color.White,
//			unfocusedContainerColor = Color.White,
//			disabledContainerColor = Color.White,
//			focusedTextColor = Color.Black,
//			unfocusedTextColor = Color.Black,
//			disabledTextColor = Color.Black,
//			focusedIndicatorColor = Color.Transparent,
//			unfocusedIndicatorColor = Color.Transparent
//		),
//		placeholder = {
//			Text(
//				text = stringResource(id = R.string.placeholder),
//				color = Color.Black
//			)
//		},
//		shape = RoundedCornerShape(16.dp),
//		keyboardActions = KeyboardActions(onSearch = { onStartSearch.invoke() }),
//		keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
//		leadingIcon = { Icon(Icons.Sharp.Search, contentDescription = "Search") },
//		trailingIcon = {
//			if (isSearching) {
//				CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(16.dp))
//			} else {
//				Icon(
//					Icons.Filled.Clear,
//					contentDescription = "Clear",
//					modifier = Modifier
//						.size(16.dp)
//						.clickable { onClearSearch.invoke() }
//				)
//			}
//		}
//	)
//}