package com.example.pixelpeppers.ui.screens

import LoadingAnimation
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pixelpeppers.ui.components.ReviewBlock
import com.example.pixelpeppers.viewModels.GameViewModel
import com.example.pixelpeppers.viewModels.ImageViewModel
import com.example.pixelpeppers.viewModels.ReviewViewModel
import com.example.pixelpeppers.viewModels.UserViewModel
import kotlinx.coroutines.runBlocking

@Composable
fun AccountScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    reviewViewModel: ReviewViewModel = hiltViewModel(),
    imageViewModel: ImageViewModel = hiltViewModel(),
    gameViewModel: GameViewModel = hiltViewModel(),
) {
    val currentUser by userViewModel.getUser().observeAsState()
    val reviews by reviewViewModel.getReviewsByUserId(currentUser?.id).observeAsState()
    val userImage by imageViewModel.getImage(currentUser?.profileImageUrl).observeAsState()

    val editDisplayNameDialog = remember { mutableStateOf(false) }

    val galleryLauncher =  rememberLauncherForActivityResult(GetContent()) { imageUri ->
        imageUri?.let {
            runBlocking {

            }
            val id = imageViewModel.createImage(imageUri)
            userViewModel.updateImage(currentUser!!.id, id)
        }
    }

    LaunchedEffect(Unit) {
        userViewModel.refreshUser()
    }

    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            reviewViewModel.refreshReviewsByUserId(currentUser!!.id)
            imageViewModel.refreshImage(currentUser!!.profileImageUrl)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (currentUser == null || reviews == null) {
            LoadingAnimation(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        } else {
            if (editDisplayNameDialog.value) {
                val displayName = remember { mutableStateOf(currentUser!!.displayName) }
                val isDisplayNameValid = remember { mutableStateOf(true) }

                Dialog(
                    onDismissRequest = {
                        editDisplayNameDialog.value = false
                    }
                ) {
                    Box(
                        modifier = Modifier
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
                                value = displayName.value,
                                onValueChange = {
                                    displayName.value = it
                                    isDisplayNameValid.value = it.isNotEmpty()
                                },
                                placeholder = {
                                    Text(
                                        "Review",
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            )
                            Button(
                                enabled = isDisplayNameValid.value,
                                onClick = {
                                    userViewModel.updateDisplayName(
                                        currentUser!!.id,
                                        displayName.value
                                    )
                                    editDisplayNameDialog.value = false
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
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 30.dp, bottom = 30.dp, start = 10.dp, end = 10.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(70.dp))
                }
                item {
                    Box {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.onTertiary,
                            modifier = Modifier
                                .size(40.dp)
                                .zIndex(1f)
                                .align(Alignment.TopEnd)
                                .clickable {
                                    galleryLauncher.launch("image/*")
                                }
                        )
                        // Image Loader
                        var image = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888).asImageBitmap()
                        if (userImage != null) {
                            image = BitmapFactory.decodeByteArray(
                                userImage!!.bytes, 0, userImage!!.bytes!!.size
                            ).asImageBitmap()
                        }
                        Image(
                            bitmap = image,
                            contentDescription = currentUser!!.displayName,
                            modifier = Modifier.size(200.dp)
                        )
                    }
                }

                item {
                    Row {
                        Text(
                            text = currentUser!!.displayName,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 25.sp,
                        )
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .size(15.dp)
                                .zIndex(1f)
                                .clickable {
                                    editDisplayNameDialog.value = true
                                }
                        )
                    }
                    Text(
                        text = "${reviews!!.size} reviews",
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                }

                items(reviews!!) { review ->
                    val game by gameViewModel.getGameById(review.gameId).observeAsState()
                    if (game == null) {
                        LoadingAnimation()
                    } else {
                        val formatted_review =
                            review.copy(title = game!!.name + " - " + review.title)
                        ReviewBlock(review = formatted_review)
                        Spacer(
                            modifier = Modifier
                                .height(10.dp)
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background)
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }
    }
}
