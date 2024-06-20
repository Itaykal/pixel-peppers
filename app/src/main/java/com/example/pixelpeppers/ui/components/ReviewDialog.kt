package com.example.pixelpeppers.ui.components

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asComposeColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.pixelpeppers.models.CreateReview
import com.example.pixelpeppers.models.Review
import com.example.pixelpeppers.models.UpdateReview
import com.example.pixelpeppers.viewModels.ImageViewModel
import com.example.pixelpeppers.viewModels.ReviewViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ReviewDialog(
    closeDialog: () -> Unit,
    gameID: Int,
    modifier: Modifier = Modifier,
    reviewID: String? = null,
    title: MutableState<String> = mutableStateOf(""),
    description: MutableState<String?> = mutableStateOf(""),
    rating: MutableState<Int> = mutableIntStateOf(1),
    imageUris: SnapshotStateList<Uri> = remember { SnapshotStateList() },
    imageViewModel: ImageViewModel = hiltViewModel(),
    reviewViewModel: ReviewViewModel = hiltViewModel(),
) {
    val previewState = rememberLazyListState()
    val isTitleValid = remember { mutableStateOf(title.value.isNotEmpty()) }
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) {
            imageUris.apply {
                clear()
                addAll(it)
            }
        }
    Dialog(onDismissRequest = closeDialog) {
        Box(
            modifier = modifier
                .background(MaterialTheme.colorScheme.onTertiary)
                .clip(shape = MaterialTheme.shapes.extraLarge)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.padding(20.dp)
            ) {
                TextField(value = title.value,
                    onValueChange = {
                        title.value = it
                        isTitleValid.value = it.isNotEmpty()
                    },
                    isError = isTitleValid.value.not(),
                    maxLines = 1,
                    placeholder = { Text("Title", color = MaterialTheme.colorScheme.onSurface) })
                TextField(value = description.value ?: "",
                    onValueChange = { description.value = it },
                    placeholder = { Text("Review", color = MaterialTheme.colorScheme.onSurface) })
                Rating(
                    rating = rating.value,
                    starSize = 20.dp,
                    onRatingChanged = { rating.value = it },
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                Button(onClick = {
                    galleryLauncher.launch("image/*")
                }) {
                    Text(
                        text = "Add Images",
                    )
                }
                LazyRow(
                    state = previewState,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp),
                ) {
                    items(imageUris) { uri ->
                        GlideImage(model = uri,
                            contentDescription = "preview",
                            transition = CrossFade,
                            contentScale = ContentScale.Crop,
                            colorFilter = PorterDuffColorFilter(
                                Color.GRAY, PorterDuff.Mode.MULTIPLY
                            ).asComposeColorFilter(),
                            modifier = modifier
                                .size(50.dp)
                                .clickable {
                                    imageUris.remove(uri)
                                }) {
                            it.diskCacheStrategy(DiskCacheStrategy.ALL)
                                .transform(RoundedCorners(70))
                        }
                    }
                }
                Row {
                    Button(
                        enabled = isTitleValid.value,
                        onClick = {
                            val imageIDs = imageUris.map { imageViewModel.createImage(it) }
                            if (reviewID == null) {
                                reviewViewModel.addReview(
                                    CreateReview(
                                        gameId = gameID,
                                        rating = rating.value,
                                        title = title.value,
                                        description = description.value,
                                        imageIDs = imageIDs
                                    )
                                )
                            } else {
                                reviewViewModel.updateReview(
                                    reviewID,
                                    UpdateReview(
                                        rating = rating.value,
                                        title = title.value,
                                        description = description.value,
                                    )
                                )
                            }
                            title.value = ""
                            description.value = ""
                            rating.value = 1
                            closeDialog()
                        }
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
