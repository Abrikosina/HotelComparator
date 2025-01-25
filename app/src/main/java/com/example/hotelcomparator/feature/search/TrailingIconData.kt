package com.example.hotelcomparator.feature.search

data class TrailingIconData(
	val iconRes: Int,
	val iconColor: Int,
	val pressedIconColor: Int? = null
) {
	var action: (() -> Unit)? = null
}
