package com.example.pixelpeppers.viewModels

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