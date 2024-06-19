package com.example.pixelpeppers.viewModels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixelpeppers.models.Review
import com.example.pixelpeppers.repositories.ImageRepository
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel
@Inject constructor(
    private val repository: ImageRepository,
) : ViewModel() {

    fun getImage(id: String): StorageReference {
       return repository.getImage(id)
    }

    fun createImage(imageUri: Uri): String{
        var id = ""
        viewModelScope.launch {
            id = repository.createImage(imageUri)
        }
        return id
    }

    fun getReviewImages(review: Review): List<StorageReference> {
        if (!review.imageIDs.isNullOrEmpty()) {
            return repository.getImagesByIds(review.imageIDs)
        }
        return emptyList()
    }
}