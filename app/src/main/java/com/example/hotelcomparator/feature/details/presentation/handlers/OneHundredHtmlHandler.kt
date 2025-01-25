package com.example.hotelcomparator.feature.details.presentation.handlers

import android.util.Log
import android.webkit.JavascriptInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import javax.inject.Inject

class OneHundredHtmlHandler @Inject constructor() : HtmlHandler {

	private val priceFlow = MutableSharedFlow<Pair<String, String>>()
	private var scope = CoroutineScope(SupervisorJob())

	@JavascriptInterface
	@Suppress("unused")
	override fun handleHtml(html: String?) {
		//Log.i("myTag", "html 101 " + html)
		val document = html?.let { Jsoup.parse(it, "UTF-8") }
//		Log.i("myTag","document 101 "+document)
//		Log.i("myTag","title "+document?.select("title"))
//
//		val kek = document?.select("div.fast-filters-tape-wrapper fast-filters-tape-wrapper_placements placements-filters-tape-wrapper-js")?.firstOrNull()
//		val price = document?.select("span.price-value placement-price-value-js")?.firstOrNull()?.text()
//		val price2 = document?.select("span.price-value")?.firstOrNull()?.text()
//		//Log.i("myTag","document?. "+document?.select("span.price-value placement-price-value-js"))
//		Log.i("myTag","perek "+document?.select("div.price-wrap"))
//		Log.i("myTag","price "+document?.select("div.price"))
//		Log.i("myTag","price "+document?.select("span.price-value placement-price-value-js"))
		//val priceWrap = document?.select("div.price-wrap")?.firstOrNull()
		val keke = document?.select("div.price")
		val price = keke?.select("span")?.firstOrNull()?.text()
		scope.launch {
			price?.let {
				if (it.any { symbol -> symbol.isDigit() }) {
					priceFlow.emit("OneHundred" to it)
				}
			}
		}
		Log.i("myTag","keke "+keke)
		Log.i("myTag","peke "+price)
	}

	override fun observePrice(): Flow<Pair<String, String>> {
		return priceFlow.asSharedFlow()
	}

	override fun cancelScope() {
		scope.cancel()
	}

	override fun toString(): String = "OneHundredHtmlHandler"
}