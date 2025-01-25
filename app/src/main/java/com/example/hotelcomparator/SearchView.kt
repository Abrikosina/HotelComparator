package com.example.hotelcomparator

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CommonSearchView(
	dateText: String,
	guestsText: String,
	destination: String,
	onSearchClick: () -> Unit,
	modifier: Modifier = Modifier
) {
	Column(
		modifier = Modifier
			.padding(horizontal = 20.dp, vertical = 8.dp)
			.fillMaxWidth()
	) {
		Text(
			text = stringResource(id = R.string.destination)
		)
		Box(
			modifier = Modifier
				.padding(top = 4.dp)
				.fillMaxWidth()
				.height(40.dp)
				.background(Color.Gray, RoundedCornerShape(100.dp))
		) {
			Text(
				text = destination, modifier = Modifier
					.align(Alignment.CenterStart)
					.padding(horizontal = 12.dp)
			)
		}
		HorizontalDivider(
			color = Color.Gray,
			thickness = 1.dp,
			modifier = Modifier.padding(top = 16.dp)
		)

		DatesAndRoomsView(dateText = dateText, guestsText = guestsText)

		HorizontalDivider(
			color = Color.Gray,
			thickness = 1.dp,
			modifier = Modifier.padding(top = 4.dp)
		)

		ButtonSearch(onSearchClick)
	}
}

@Composable
fun ButtonSearch(onSearchClick: () -> Unit) {
	Button(
		onClick = onSearchClick, modifier = Modifier
			.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
			containerColor = Color.Blue,
			contentColor = Color.White
		)
	) {
		Text(
			text = stringResource(id = R.string.search),
			modifier = Modifier
				.align(Alignment.CenterVertically)
		)
	}
}

@Composable
fun DatesAndRoomsView(dateText: String, guestsText: String) {
	Row(
		modifier = Modifier
			.padding(top = 8.dp)
			.height(IntrinsicSize.Min)
	) {
		DatesOrRoomsItemView(
			text = dateText,
			textResource = R.string.date,
			modifier = Modifier.weight(1f)
		)
		VerticalDivider(
			color = Color.Gray,
			thickness = 1.dp,
			//modifier = Modifier.wrapContentSize()
		)
		DatesOrRoomsItemView(
			text = guestsText,
			textResource = R.string.guests,
			modifier = Modifier
				.weight(1f)
				.padding(start = 16.dp)
		)
	}
}

@Composable
fun DatesOrRoomsItemView(text: String, textResource: Int, modifier: Modifier = Modifier) {
	Column(modifier = modifier) {
		Text(
			text = stringResource(id = textResource),
			modifier = Modifier.padding(top = 2.dp)
		)

		Text(
			text = text,
			modifier = Modifier.padding(top = 2.dp, bottom = 2.dp)
		)
	}
}

@Composable
fun CustomVerticalDivider(
	modifier: Modifier = Modifier,
	thickness: Dp = DividerDefaults.Thickness,
	color: Color = DividerDefaults.color,
) = Canvas(
	modifier = Modifier
		.width(thickness)
		.then(modifier)
) {
	drawLine(
		color = color,
		strokeWidth = thickness.toPx(),
		start = Offset(thickness.toPx() / 2, 0f),
		end = Offset(thickness.toPx() / 2, size.height),
	)
}

@Preview
@Composable
fun CommonSearchViewPreview() {
	CommonSearchView(
		dateText = "22-23 авг",
		guestsText = "2 гостя",
		destination = "Париж",
		onSearchClick = {})
}