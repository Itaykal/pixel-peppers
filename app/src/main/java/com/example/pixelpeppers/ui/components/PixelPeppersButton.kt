package com.example.pixelpeppers.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PixelPeppersButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
//    var isPressed by remember { mutableStateOf(false) }
//
//    val backgroundColor = if (isPressed) Color(0xff2d5f3d) else Color(0xff3d7151)
    val backgroundColor = Color(0xff3d7151)

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .offset(x = (-0.5).dp, y = 711.dp)
            .requiredWidth(width = 340.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = backgroundColor)
            .padding(horizontal = 10.dp, vertical = 13.dp)
            .clickable(
                onClick = onClick,
                onClickLabel = text,
            )
    ) {
        BasicText(
            text = text,
            style = TextStyle(
                color = Color.White,
                fontSize = 18.sp
            )
        )
    }
}