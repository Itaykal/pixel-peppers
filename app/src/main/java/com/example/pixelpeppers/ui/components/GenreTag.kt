package com.example.pixelpeppers.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreTag(
    text: String,
    modifier: Modifier = Modifier,
    clickable: Boolean = true,
    activeColor: Color = MaterialTheme.colorScheme.primaryContainer,
    inactiveColor: Color = MaterialTheme.colorScheme.onTertiary
) {
    var selected by remember { mutableStateOf(false) }
    if (clickable) {
        FilterChip(
            onClick = { selected =! selected },
            selected = selected,
            modifier = modifier,
            label = {
                Text(
                    text = text,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium)
                )
            },
            shape = MaterialTheme.shapes.extraSmall,
            colors = FilterChipDefaults.filterChipColors(
                containerColor = inactiveColor,
            ),
            border =  FilterChipDefaults.filterChipBorder(
                borderColor = inactiveColor
            )
        )
    } else {
        SuggestionChip(
            onClick = {},
            modifier = modifier,
            label = {
                Text(
                    text = text,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium)
                )
            },
            shape = MaterialTheme.shapes.extraSmall,
            colors = SuggestionChipDefaults.suggestionChipColors(
                labelColor = activeColor,
                containerColor = activeColor,
            ),
            border =  SuggestionChipDefaults.suggestionChipBorder(
                borderColor = activeColor
            )
        )
    }
}
