package com.example.pixelpeppers.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pixelpeppers.models.Review
import com.example.pixelpeppers.viewModels.ImageViewModel
import com.google.firebase.storage.StorageReference

@Composable
fun ReviewEditDialog(
    review: Review,
    closeDialog: () -> Unit,
    imageViewModel: ImageViewModel = hiltViewModel(),
) {
    val imageList = remember { mutableStateListOf<Any>() }
    LaunchedEffect(Unit) {
        imageList.addAll(imageViewModel.getReviewImages(review))
    }

    ReviewDialog(
        title = remember { mutableStateOf(review.title) },
        description = remember { mutableStateOf(review.description) },
        rating = remember { mutableIntStateOf(review.rating) },
        imageModels = imageList,
        gameID = review.gameId,
        editReviewID = review.id,
        onDismiss = closeDialog,
        onSubmit = closeDialog,
    )
}