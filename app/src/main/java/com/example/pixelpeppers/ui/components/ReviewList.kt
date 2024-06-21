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
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pixelpeppers.models.Review

fun LazyListScope.reviewList(
    reviews: List<Review>,
    addGameName: Boolean = false,
    onEdit: (Review) -> Unit = {},
    editable: Boolean = false,
) {
    if (reviews.isEmpty()) {
        item {
            Text(text="No reviews yet", color = MaterialTheme.colorScheme.onBackground,)
        }
    }
    itemsIndexed(reviews, key = { _, review -> review.id }) { index, review ->
        ReviewBlock(
            review = review,
            addGameName = addGameName,
            onEdit = { r -> onEdit(r) },
            editable=editable,
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
}