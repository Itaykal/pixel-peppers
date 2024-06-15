package com.example.pixelpeppers.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewDialog(
    onSubmit: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var ratingExpanded = remember { mutableStateOf(false) }
    var selectedRating = remember { mutableStateOf("1") }


    Dialog(onDismissRequest = {}) {
        Box(
            modifier = modifier
                .background(MaterialTheme.colorScheme.onTertiary)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .padding(20.dp)
            ) {
                TextField(
                    value = "",
                    onValueChange = {},
                    maxLines = 1,
                    placeholder = { Text("Title", color = MaterialTheme.colorScheme.onSurface) }
                )
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Review", color = MaterialTheme.colorScheme.onSurface) }
                )
//                ExposedDropdownMenuBox(
//                    expanded = ratingExpanded.value,
//                    modifier = Modifier
//                        .clickable{ ratingExpanded.value = true },
//                    onExpandedChange = { ratingExpanded.value != ratingExpanded.value }
//                ) {
//                    TextField(
//                        value = selectedRating.value,
//                        onValueChange = {},
//                        readOnly = true,
//                        modifier = Modifier.menuAnchor(),
//                        trailingIcon = {
//                            ExposedDropdownMenuDefaults.TrailingIcon(
//                                expanded = ratingExpanded.value
//                            )
//                        }
//                    )
//                    ExposedDropdownMenu(
//                        expanded = ratingExpanded.value,
//                        onDismissRequest = { ratingExpanded.value = false }
//                    ) {
//                        repeat(5) {index ->
//                            val rating = (index + 1).toString()
//
//                            DropdownMenuItem(
//                                text = { Text(rating) },
//                                onClick = {
//                                    ratingExpanded.value = false
//                                    selectedRating.value = rating
//                                }
//                            )
//                        }
//                    }
//                }
                Row() {
                    Button(onClick = onCancel) {
                        Text(text = "Cancel")
                    }
                    Button(onClick = onSubmit) {
                        Text(text = "Submit")
                    }
                }
            }
        }
    }
}
