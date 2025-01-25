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

class OstrovokHtmlHandler @Inject constructor() : HtmlHandler {

	private val priceFlow = MutableSharedFlow<Pair<String,String>>()
	private var scope = CoroutineScope(SupervisorJob())

	@JavascriptInterface
	@Suppress("unused")
	override fun handleHtml(html: String?) {
		Log.i("myTag", "html " + html)
		val document = Jsoup.parse(html, "UTF-8")
		//Log.i("myTag","PEPE "+document.select("div.HotelHeader_price__MdKA3"))
		val price: String? = document.select("div.Button_content__1ypi3").text()
		scope.launch {
			price?.let {
				if (it.contains(RUB)) {
					Log.i("myTag","Meremore")
					priceFlow.emit("Ostrovok" to it.takeOnlyPrice())
				}
			}
		}
		Log.i("myTag", "price " + price)
	}

	override fun observePrice(): Flow<Pair<String, String>> {
		return priceFlow.asSharedFlow()
	}

	override fun cancelScope() {
		scope.cancel()
	}

	override fun toString(): String = "OstrovokHtmlHandler"

	private fun String.takeOnlyPrice(): String {
		val priceWithText = substringBefore(RUB).trim()
		Log.i("myTag","mushu "+priceWithText.filter { it.isDigit() })
		return priceWithText.filter { it.isDigit() }
	}

	companion object {
		private const val RUB = "₽"
	}
}