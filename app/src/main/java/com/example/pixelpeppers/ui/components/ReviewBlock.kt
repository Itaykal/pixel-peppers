package com.example.pixelpeppers.ui.components

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asComposeColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.pixelpeppers.R
import com.example.pixelpeppers.models.Review

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ReviewBlock(
    review: Review,
    modifier: Modifier = Modifier,
) {
    val minimumLineLength = 4
    val expandedState = remember { mutableStateOf(false) }
    val showReadMoreButtonState = remember { mutableStateOf(false) }
    val maxLines = if (expandedState.value) 200 else minimumLineLength

    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        // @@ TODO: Add uID and user image fetching to viewmodel.
        Box (
            modifier = Modifier
                .clip(CircleShape)
        ) {
            Image(
                painter = painterResource(id = R.drawable.user),
                contentDescription = review.authorDisplayName,
                //colorFilter = ColorFilter.tint(Color.White), //PLACEHOLDER PLEASE REMOVE WHEN HAVE PROFILE PIC
                modifier = Modifier
                    .size(40.dp)
            )
        }
        Column (
            modifier = Modifier.padding(end = 30.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            // Rating Stars and User name
            Row (
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = review.authorDisplayName,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    modifier = Modifier
                        .alpha(0.8f),
                )
                Spacer(modifier = Modifier.weight(1f))
                Rating(
                    rating = review.rating,
                    modifier = Modifier
                        .alpha(0.5f)
                )
            }
            Row (

            ) {
                Text(
                    text = review.title,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                )
            }
            // Pictures
            if (review.mediaURLs != null) {
                if (review.mediaURLs.isNotEmpty()) {
                    val showImageDialog = remember { mutableStateOf(false) }
                    val zoomedImage = remember { mutableStateOf("") }
                    val mediaListState = rememberLazyListState()

                    if (showImageDialog.value) {
                        Dialog( onDismissRequest = {
                            showImageDialog.value = false
                            zoomedImage.value = ""
                        }) {
                            GlideImage(
                                model = zoomedImage.value,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(300.dp)
                                    .clip(MaterialTheme.shapes.large)
                            )
                        }

                    }

                    LazyRow (
                        state = mediaListState,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 10.dp),
                    ) {
                        items(review.mediaURLs) { url ->
                            GlideImage(
                                model = url,
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                colorFilter = PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY).asComposeColorFilter(),
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(MaterialTheme.shapes.large)
                                    .clickable {
                                        showImageDialog.value = true
                                        zoomedImage.value = url
                                    }
                            )
                        }
                    }
                }
            }

            // Review Content with read more option
            Row (

            ) {
                Text(
                    text = review.description!!,
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = maxLines,
                    onTextLayout = { textLayoutResult: TextLayoutResult ->
                        if (textLayoutResult.lineCount > minimumLineLength - 1) {
                            if (textLayoutResult.isLineEllipsized(minimumLineLength - 1)) showReadMoreButtonState.value =
                                true
                        }
                    },
                )
            }
            if (showReadMoreButtonState.value) {
                Text(
                    text = if (expandedState.value) "Show Less" else "Read More",
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .alpha(0.4f)
                        .clickable {
                            expandedState.value = !expandedState.value
                        },

                    )
            }
        }
    }
}