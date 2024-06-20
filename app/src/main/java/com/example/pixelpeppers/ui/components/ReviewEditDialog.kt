package com.example.pixelpeppers.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.pixelpeppers.models.Review

@Composable
fun ReviewEditDialog(
    review: Review,
    closeDialog: () -> Unit,
    ) {
    ReviewDialog(
        title = remember { mutableStateOf(review.title) },
        description = remember { mutableStateOf(review.description) },
        rating = remember { mutableIntStateOf(review.rating) },
        gameID = review.gameId,
        reviewID = review.id,
        closeDialog = closeDialog,
    )
}