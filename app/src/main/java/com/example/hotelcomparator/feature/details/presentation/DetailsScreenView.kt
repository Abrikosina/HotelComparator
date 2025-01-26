package com.example.hotelcomparator.feature.details.presentation

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebView
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.hotelcomparator.R
import com.example.hotelcomparator.feature.details.presentation.handlers.HtmlHandler
import com.example.hotelcomparator.feature.details.presentation.model.WebSiteItem
import com.kevinnzou.web.AccompanistWebViewClient
import com.kevinnzou.web.WebView
import com.kevinnzou.web.rememberWebViewState


@Composable
fun DetailsScreenView(name: String, viewModel: DetailsViewModel = hiltViewModel()) {
	val lifecycleOwner = LocalLifecycleOwner.current
	val state by viewModel.screenState.collectAsStateWithLifecycle(lifecycleOwner = lifecycleOwner)
	DetailsContent(state, viewModel)
	LaunchedEffect(true) {
		viewModel.onStart(name = name)
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailsContent(
	state: DetailsScreenState,
	viewModel: DetailsViewModel
) {
	Column(modifier = Modifier.fillMaxSize()) {
		DetailsPager(
			imageUrl = state.currentImageUrl,
			currentPage = state.currentPage,
			pageCount = state.details?.photoCount?.toInt(),
			viewModel = viewModel
		)
		TitleBlock(
			name = state.details?.name.orEmpty(),
			reviewImageUrl = state.details?.ratingImageUrl.orEmpty(),
			ranking = state.details?.rankingData?.rankingString.orEmpty(),
			reviewsCount = state.details?.numReviews.orEmpty()
		)
		DateItem(
			"19 дек. - 21 дек.",
			2
		) {
			viewModel.showDatePicker(true)
		}
		InformationBlock(
			amenities = state.details?.amenities.orEmpty()
		)
		if (state.isShowDatePicker) {
			DateRangePickerModal(onDateRangeSelected = { startDate: Long?, endDate: Long? ->
				viewModel.onChangeDate(
					startDate,
					endDate
				)
			}, onDismiss = { viewModel.showDatePicker(false) })
		}
		if (state.webSiteItems.isNotEmpty()) {
			LoadingWebPages(state.webSiteItems)
		}
	}
}

@Composable
private fun LoadingWebPages(webSiteItems: List<WebSiteItem>) {
	LazyColumn {
		items(webSiteItems) { item ->
			WebPageUrl(item.isLoading, item.url, item.handler, item.price)
		}
	}
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun WebPageUrl(isLoading: Boolean, siteUrl: String, handler: HtmlHandler, price: String) {
	val state = rememberWebViewState(siteUrl)
	val webClient = remember {
		object : AccompanistWebViewClient() {

			override fun onPageFinished(view: WebView, url: String?) {
				super.onPageFinished(view, url)
//				Log.i("myTag", "Mekeke " + url)
//				Log.i("myTag", "Mekekew " + siteUrl)
				if (url == siteUrl) {
					view.loadUrl("javascript:${handler}.handleHtml(document.documentElement.outerHTML);")
				}
			}
		}
	}

	Box(
		modifier = Modifier
			.fillMaxWidth()
			.height(100.dp)
	) {
		if (isLoading) {
		WebView(
			state = state,
			modifier = Modifier.alpha(0f),
			onCreated = { webView ->
				webView.settings.javaScriptEnabled = true
				CookieManager.getInstance().setAcceptCookie(true)
				CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
				webView.settings.domStorageEnabled = true
				webView.settings.databaseEnabled = true
				webView.addJavascriptInterface(handler, "$handler")
			},
			client = webClient
		)
			//Box(modifier = Modifier.shimmerLoading())
			Box(modifier = Modifier.fillMaxWidth()
				.height(100.dp).shimmerLoading())
		} else {
			HotelPrice(price = price)
		}
	}
}

@Composable
fun HotelPrice(price: String) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		Image(
			painter = painterResource(id = R.drawable.ic_launcher_background),
			contentDescription = "",
			modifier = Modifier
				.height(60.dp)
				.width(100.dp)
		)
		Text(text = price, fontSize = 20.sp)
	}
}

@Composable
fun Modifier.shimmerLoading(
	durationMillis: Int = 1000,
): Modifier {
	val transition = rememberInfiniteTransition(label = "")

	val translateAnimation by transition.animateFloat(
		initialValue = 0f,
		targetValue = 500f,
		animationSpec = infiniteRepeatable(
			animation = tween(
				durationMillis = durationMillis,
				easing = LinearEasing,
			),
			repeatMode = RepeatMode.Restart,
		),
		label = "",
	)

	return drawBehind {
		drawRect(
			brush = Brush.linearGradient(
				colors = listOf(
					Color.LightGray.copy(alpha = 0.2f),
					Color.LightGray.copy(alpha = 1.0f),
					Color.LightGray.copy(alpha = 0.2f),
				),
				start = Offset(x = translateAnimation, y = translateAnimation),
				end = Offset(x = translateAnimation + 100f, y = translateAnimation + 100f),
			)
		)
	}
}

@Composable
private fun RatingBlock(imageUrl: String, ranking: String, reviewsCount: String) {
	Column {
		Row {
			AsyncImage(
				model = ImageRequest.Builder(LocalContext.current)
					.data(imageUrl)
					.decoderFactory(SvgDecoder.Factory())
					.build(), contentDescription = "", modifier = Modifier
					.height(20.dp)
					.width(80.dp)
			)
			Text(
				text = stringResource(id = R.string.reviews_count, reviewsCount),
				modifier = Modifier.padding(start = 12.dp),
				fontSize = 14.sp
			)
		}

		Text(
			text = ranking,
			modifier = Modifier.padding(top = 2.dp),
			fontSize = 14.sp
		)
		Text(
			text = stringResource(id = R.string.write_review),
			modifier = Modifier.padding(top = 8.dp),
			fontSize = 14.sp
		)
	}
}

@Composable
private fun TitleBlock(
	name: String,
	reviewImageUrl: String,
	ranking: String,
	reviewsCount: String
) {
	Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
		Text(text = name, fontSize = 26.sp)
		RatingBlock(imageUrl = reviewImageUrl, ranking = ranking, reviewsCount = reviewsCount)
	}
}

@Composable
private fun InformationBlock(amenities: List<String>) {
	Column {
		Text(text = stringResource(id = R.string.information), fontSize = 18.sp)
		Spacer(modifier = Modifier.height(8.dp))
		AmenityBlock(amenities = amenities)
	}
}

@Composable
private fun AmenityBlock(amenities: List<String>) {
	LazyVerticalGrid(
//		modifier = Modifier
//			.padding(6.dp),
		columns = GridCells.Adaptive(minSize = 80.dp),
		//columns = GridCells.Fixed(3),
		verticalArrangement = Arrangement.spacedBy(16.dp),
		horizontalArrangement = Arrangement.spacedBy(16.dp)
	) {
		val amenitiesCount = if (amenities.size > 3) 4 else amenities.size
		items(amenitiesCount) { index ->
			AmenityItem(amenities[index])
		}
	}
}

@Composable
private fun AmenityItem(name: String) {
	Box(
		modifier = Modifier
			.height(30.dp)
			//.wrapContentWidth()
			//.widthIn(min = 120.dp, max = 240.dp)
			.background(Color.Gray, RoundedCornerShape(16.dp))
			.clip(RoundedCornerShape(16.dp))
			.border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(16.dp)),
		contentAlignment = Alignment.Center
	) {
		Text(
			text = name,
			modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp),
			//maxLines = 1,
			color = Color.White,
			fontSize = 12.sp
		)
	}
}

@Composable
private fun DateItem(date: String, guestsCount: Int, showDatePicker: () -> Unit) {
	Row(
		modifier = Modifier
			.padding(horizontal = 20.dp)
			.height(48.dp)
			.fillMaxWidth()
			.clickable { showDatePicker.invoke() }
			.background(Color.Gray, RoundedCornerShape(16.dp))
			.clip(RoundedCornerShape(16.dp))
			.border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(16.dp)),
		verticalAlignment = Alignment.CenterVertically
	) {
		Row(
			modifier = Modifier
				.weight(2f)
				.padding(vertical = 4.dp)
		) {
			Icon(
				imageVector = Icons.Default.DateRange,
				contentDescription = "",
				modifier = Modifier
					.padding(start = 16.dp)
					.size(16.dp)
			)

			Text(
				text = date,
				modifier = Modifier.padding(horizontal = 4.dp),
				color = Color.White,
				fontSize = 12.sp
			)
		}

		VerticalDivider(
			color = Color.Red,
			thickness = 1.dp,
		)

		Row(
			modifier = Modifier
				.weight(1f)
				.padding(horizontal = 16.dp),
		) {

			Box(
				modifier = Modifier
					.weight(1f)
					.padding(vertical = 4.dp)
			) {
				Icon(
					imageVector = Icons.Default.Build,
					contentDescription = "",
					modifier = Modifier
						.padding(start = 16.dp)
						.size(16.dp)
				)

				Text(
					text = "1",
					modifier = Modifier.padding(horizontal = 4.dp),
					color = Color.White,
					fontSize = 12.sp
				)
			}

			Box(
				modifier = Modifier
					.weight(1f)
					.padding(vertical = 4.dp)
			) {
				Icon(
					imageVector = Icons.Default.Face,
					contentDescription = "",
					modifier = Modifier
						.padding(start = 16.dp)
						.size(16.dp)
				)

				Text(
					text = guestsCount.toString(),
					modifier = Modifier.padding(horizontal = 4.dp),
					color = Color.White,
					fontSize = 12.sp
				)
			}
		}
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DetailsPager(
	imageUrl: String,
	currentPage: Int,
	pageCount: Int?,
	pagerState: PagerState = rememberPagerState(pageCount = { pageCount ?: 1 }),
	viewModel: DetailsViewModel
) {
	Box(
		modifier = Modifier
			.height(200.dp)
			.fillMaxWidth()
	) {
		HorizontalPager(
			state = pagerState
		) { _ ->
			AsyncImage(
				model = imageUrl,
				contentDescription = null,
				contentScale = ContentScale.Crop,
				modifier = Modifier.fillMaxSize()
			)
		}
		Text(
			text = "$currentPage из $pageCount",
			color = Color.Black,
			fontSize = 12.sp,
			modifier = Modifier
				.padding(horizontal = 8.dp, vertical = 4.dp)
				.align(
					Alignment.BottomEnd
				)
				.background(Color.White, RoundedCornerShape(16.dp))
		)
	}
	LaunchedEffect(pagerState) {
		Log.i("myTag", "pagerState.currentPage " + pagerState.currentPage)
		snapshotFlow { pagerState.currentPage }.collect {
			viewModel.onChangePage(it)

		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerModal(
	onDateRangeSelected: (startDate: Long?, endDate: Long?) -> Unit,
	onDismiss: () -> Unit
) {
	val dateRangePickerState = rememberDateRangePickerState()

	DatePickerDialog(
		onDismissRequest = onDismiss,
		confirmButton = {
			TextButton(
				onClick = {
					onDateRangeSelected(
						dateRangePickerState.selectedStartDateMillis,
						dateRangePickerState.selectedEndDateMillis
					)
					onDismiss()
				}
			) {
				Text("OK")
			}
		},
		dismissButton = {
			TextButton(onClick = onDismiss) {
				Text("Cancel")
			}
		}
	) {
		DateRangePicker(
			state = dateRangePickerState,
			title = {
				Text(
					text = "Select date range"
				)
			},
			showModeToggle = false,
			modifier = Modifier
				.fillMaxWidth()
				.height(500.dp)
				.padding(16.dp)
		)
	}
}


