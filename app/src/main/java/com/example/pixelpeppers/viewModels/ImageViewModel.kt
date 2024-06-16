package com.example.pixelpeppers.viewModels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixelpeppers.models.Image
import com.example.pixelpeppers.models.Review
import com.example.pixelpeppers.repositories.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel
@Inject constructor(
    private val repository: ImageRepository,
) : ViewModel() {

    fun getImage(id: String?): LiveData<Image> {
        if (id == null) {
            return MutableLiveData()
        }
       return repository.getImage(id)
    }

    suspend fun refreshImage(id: String) {
        viewModelScope.launch {
            repository.refreshImage(id)
        }
    }

    fun createImage(imageUri: Uri): String{
        var id = ""
        viewModelScope.launch {
            id = repository.createImage(imageUri)
        }
        return id
    }

    fun refreshImages(review: Review) {
        if (!review.imageIDs.isNullOrEmpty()) {
            viewModelScope.launch {
                repository.refreshImages(review.imageIDs)
            }
        }
    }

    fun getReviewImages(review: Review): LiveData<List<Image>> {
        if (!review.imageIDs.isNullOrEmpty()) {
            return repository.getImagesByIds(review.imageIDs)
        }
        return MutableLiveData<List<Image>>()
    }
}