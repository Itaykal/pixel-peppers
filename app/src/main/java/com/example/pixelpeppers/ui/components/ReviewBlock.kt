package com.example.pixelpeppers.ui.components

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asComposeColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.pixelpeppers.models.Review
import com.example.pixelpeppers.viewModels.GameViewModel
import com.example.pixelpeppers.viewModels.ImageViewModel
import com.example.pixelpeppers.viewModels.ReviewViewModel
import com.example.pixelpeppers.viewModels.UserViewModel
import com.google.firebase.storage.StorageReference
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ReviewBlock(
    review: Review,
    modifier: Modifier = Modifier,
    onFinishLoading: () -> Unit = {},
    addGameName: Boolean = false,
    imageViewModel: ImageViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    gameViewModel: GameViewModel = hiltViewModel(),
    reviewViewModel: ReviewViewModel = hiltViewModel(),
) {
    val author by userViewModel.getUser(review.authorId).observeAsState()
    val game by gameViewModel.getGameById(review.gameId).observeAsState()
    val minimumLineLength = 4
    val expandedState = remember { mutableStateOf(false) }
    val showReadMoreButtonState = remember { mutableStateOf(false) }
    val maxLines = if (expandedState.value) 200 else minimumLineLength

    val imageList = remember { mutableStateListOf<StorageReference>() }
    val authorImage = remember { mutableStateOf<StorageReference?>(null) }

    val deletingState = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        userViewModel.refreshUser(review.authorId)
        gameViewModel.refreshGamesByID(review.gameId)
        imageList.addAll(imageViewModel.getReviewImages(review))
    }

    LaunchedEffect(author) {
        author?.let { authorImage.value = imageViewModel.getImage(author!!.profileImageUrl) }
    }
    if (author != null && authorImage.value != null && game != null) {
        LaunchedEffect(Unit) {
            onFinishLoading()
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.Top,
            modifier = modifier.background(MaterialTheme.colorScheme.background)
        ) {
            Box(
                modifier = modifier.clip(CircleShape)
            ) {
                GlideImage(
                    model = authorImage.value,
                    contentDescription = author!!.displayName,
                    modifier = Modifier.size(35.dp)
                )
            }
            Column(
                modifier = modifier.padding(end = 30.dp)
            ) {
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
                        modifier = modifier.alpha(0.8f),
                    )
                    Spacer(modifier = modifier.weight(1f))
                    Rating(
                        rating = review.rating, modifier = modifier.alpha(0.5f)
                    )
                    // Edit icons
                    if (Firebase.auth.currentUser?.uid == review.authorId) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "Edit",
                            modifier = modifier
                                .size(16.dp)
                                .clickable { },
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Delete",
                            modifier = modifier
                                .size(16.dp)
                                .clickable {
                                    deletingState.value = true
                                    reviewViewModel.deleteReview(review.id)
                                },
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
                Row {
                    var reviewTitle = review.title
                    if (addGameName) {
                        reviewTitle = game!!.name + " - " + reviewTitle
                    }
                    if (reviewTitle.length > 40) {
                        reviewTitle = reviewTitle.substring(0, 40) + "..."
                    }
                    Text(
                        text = reviewTitle,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                    )
                }
                // Pictures
                if (review.imageIDs != null) {
                    val mediaListState = rememberLazyListState()
                    val showImageDialog = remember { mutableStateOf(false) }
                    val zoomedImage = remember { mutableStateOf<StorageReference?>(null) }

                    LazyRow(
                        state = mediaListState,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 10.dp),
                    ) {
                        items(imageList) { image ->
                            GlideImage(
                                model = image,
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
                                    }
                            )
                        }
                    }

                    if (showImageDialog.value) {
                        Dialog(onDismissRequest = {
                            showImageDialog.value = false
                            zoomedImage.value = null
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

                }

                // Review Content with read more option
                Row {
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
                        modifier = modifier
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