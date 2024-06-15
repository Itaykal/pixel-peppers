package com.example.pixelpeppers.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pixelpeppers.R

@Composable
fun Rating(
    stars: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        repeat(stars) {
            Icon(
                painter = painterResource(id = R.drawable.star),
                contentDescription = "",
                tint = Color(0xFFF9A826),
                modifier = Modifier
                    .size(12.dp)
            )
        }
        repeat(5 - stars) {
            Icon(
                painter = painterResource(id = R.drawable.empty_star),
                contentDescription = "",
                tint = Color.Gray,
                modifier = Modifier
                    .size(12.dp)
            )
        }
    }
}