package com.example.pixelpeppers.ui.components

import LoadingAnimation
import android.graphics.BitmapFactory
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asComposeColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.pixelpeppers.R
import com.example.pixelpeppers.models.Image
import com.example.pixelpeppers.models.Review
import com.example.pixelpeppers.viewModels.ImageViewModel
import com.example.pixelpeppers.viewModels.UserViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ReviewBlock(
    review: Review,
    modifier: Modifier = Modifier,
    imageViewModel: ImageViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
) {
    val author by userViewModel.getUser(review.authorId).observeAsState()
    val imageList by imageViewModel.getReviewImages(review).observeAsState()
    val authorImage by imageViewModel.getImage(author?.profileImageUrl).observeAsState()
    val minimumLineLength = 4
    val expandedState = remember { mutableStateOf(false) }
    val showReadMoreButtonState = remember { mutableStateOf(false) }
    val maxLines = if (expandedState.value) 200 else minimumLineLength


    LaunchedEffect(Unit) {
        imageViewModel.refreshImages(review)
        userViewModel.refreshUser(review.authorId)
    }
    LaunchedEffect(author) {
        if (author != null) {
            println("Refreshing Image")
            imageViewModel.refreshImage(author!!.profileImageUrl)
        }
    }
    if (author == null || authorImage == null) {
        LoadingAnimation(
            modifier = modifier.padding(
                horizontal = 10.dp
            )
        )
    } else {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.Top,
            modifier = modifier.background(MaterialTheme.colorScheme.background)
        ) {
            // @@ TODO: Add uID and user image fetching to viewmodel.
            Box(
                modifier = Modifier.clip(CircleShape)
            ) {
                Image(
                    bitmap = BitmapFactory.decodeByteArray(
                        authorImage!!.bytes, 0, authorImage!!.bytes!!.size
                    ).asImageBitmap(),
                    contentDescription = author!!.displayName,
                    modifier = Modifier.size(40.dp)
                )
            }
            Column(
                modifier = Modifier.padding(end = 30.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                // Rating Stars and User name
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = author!!.displayName,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 1,
                        modifier = Modifier.alpha(0.8f),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Rating(
                        rating = review.rating, modifier = Modifier.alpha(0.5f)
                    )
                }
                Row {
                    Text(
                        text = review.title,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                    )
                }
                // Pictures
                if (review.imageIDs != null) {
                    if (!imageList.isNullOrEmpty()) {
                        val mediaListState = rememberLazyListState()
                        val showImageDialog = remember { mutableStateOf(false) }
                        val zoomedImage = remember { mutableStateOf<Image?>(null) }

                        LazyRow(
                            state = mediaListState,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, bottom = 10.dp),
                        ) {
                            items(imageList!!) { image ->
                                Image(bitmap = BitmapFactory.decodeByteArray(
                                    image.bytes, 0, image.bytes!!.size
                                ).asImageBitmap(),
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                    colorFilter = PorterDuffColorFilter(
                                        Color.GRAY, PorterDuff.Mode.MULTIPLY
                                    ).asComposeColorFilter(),
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(MaterialTheme.shapes.large)
                                        .clickable {
                                            showImageDialog.value = true
                                            zoomedImage.value = image
                                        })
                            }
                        }

                        if (showImageDialog.value) {
                            Dialog(onDismissRequest = {
                                showImageDialog.value = false
                                zoomedImage.value = null
                            }) {
                                Image(
                                    bitmap = BitmapFactory.decodeByteArray(
                                        zoomedImage.value!!.bytes,
                                        0,
                                        zoomedImage.value!!.bytes!!.size
                                    ).asImageBitmap(),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(300.dp)
                                        .clip(MaterialTheme.shapes.large)
                                )
                            }

                        }
                    }
                }

                // Review Content with read more option
                Row(

                ) {
                    Text(
                        text = review.description ?: "",
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
}