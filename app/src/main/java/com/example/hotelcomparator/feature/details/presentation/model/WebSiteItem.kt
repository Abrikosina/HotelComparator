package com.example.hotelcomparator.feature.details.presentation.model

import com.example.hotelcomparator.feature.details.presentation.handlers.HtmlHandler

data class WebSiteItem(
	val name: String,
	val isLoading: Boolean,
	val url: String,
	val handler: HtmlHandler,
	val price: String = ""
)
