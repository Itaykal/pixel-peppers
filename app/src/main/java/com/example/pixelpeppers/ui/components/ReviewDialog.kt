package com.example.pixelpeppers.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun ReviewDialog(
    onSubmit: (title: String, description: String, rating: Int) -> Unit,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
) {
    var rating = remember { mutableIntStateOf(1) } //default rating will be 1
    var isTitleValid = remember { mutableStateOf(false) }
    var title = remember { mutableStateOf("") }
    var review = remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            modifier = modifier
                .background(MaterialTheme.colorScheme.onTertiary)
                .clip(shape = MaterialTheme.shapes.extraLarge)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .padding(20.dp)
            ) {
                TextField(
                    value = title.value,
                    onValueChange = {
                        title.value = it
                        isTitleValid.value = it.isNotEmpty()
                    },
                    isError = isTitleValid.value.not(),
                    maxLines = 1,
                    placeholder = { Text("Title", color = MaterialTheme.colorScheme.onSurface) }
                )
                TextField(
                    value = review.value,
                    onValueChange = { review.value = it },
                    placeholder = { Text("Review", color = MaterialTheme.colorScheme.onSurface) }
                )
                Rating(
                    rating = rating.intValue,
                    starSize = 20.dp,
                    onRatingChanged = { rating.intValue = it },
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                )
                Row() {
                    Button(
                        enabled = isTitleValid.value,
                        onClick = { onSubmit(title.value, review.value, rating.intValue) }
                    ) {
                        Text(
                            text = "Submit"
                        )
                    }
                }
            }
        }
    }
}
