package com.example.pixelpeppers.ui.components

import LoadingAnimation
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pixelpeppers.models.Review

fun LazyListScope.reviewList(
    reviews: List<Review>?,
    addGameName: Boolean = false,
    onEdit: (Review) -> Unit = {}
) {
    val loading: Array<Boolean> = reviews!!.map { true }.toTypedArray()

    itemsIndexed(reviews) { index, review ->
        ReviewBlock(
            review = review,
            onFinishLoading = { loading[index] = false },
            addGameName = addGameName,
            onEdit = { r -> onEdit(r) }
        )
        Spacer(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        )
        if (index == reviews.size - 1) {
            Spacer(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
            )
        }
    }
    if (loading.any { it }) {
        item {
            LoadingAnimation()
        }
    }
}