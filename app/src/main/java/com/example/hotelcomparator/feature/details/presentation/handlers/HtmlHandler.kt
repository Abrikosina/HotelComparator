package com.example.hotelcomparator.feature.details.presentation.handlers

import android.webkit.JavascriptInterface
import kotlinx.coroutines.flow.Flow

interface HtmlHandler {
	@JavascriptInterface
	@Suppress("unused")
	fun handleHtml(html: String?)

	fun observePrice() : Flow<Pair<String, String>>
	fun cancelScope()
}