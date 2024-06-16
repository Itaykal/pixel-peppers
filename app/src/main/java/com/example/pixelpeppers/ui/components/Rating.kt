package com.example.pixelpeppers.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.pixelpeppers.R

private val DEFAULT = { _: Int -> throw UnsupportedOperationException("this shouldn't be called") }

@Composable
fun Rating(
    rating: Int,
    modifier: Modifier = Modifier,
    starSize: Dp = 12.dp,
    onRatingChanged: (Int) -> Unit = DEFAULT
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        repeat(5) { index ->
            val currentStar = index + 1
            val isSelected = currentStar <= rating
            val icon = if (isSelected) painterResource(id = R.drawable.star) else painterResource(id = R.drawable.empty_star)
            val tint = if (isSelected) Color(0xFFF9A826) else Color.Gray

            val iconMod = if (onRatingChanged == DEFAULT) Modifier else Modifier.selectable(selected = isSelected, onClick = { onRatingChanged(currentStar) })

            Icon(
                painter = icon,
                contentDescription = "",
                tint = tint,
                modifier = iconMod
                    .size(starSize)

            )
        }
    }
}