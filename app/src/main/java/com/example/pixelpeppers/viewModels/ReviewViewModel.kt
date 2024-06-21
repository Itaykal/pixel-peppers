package com.example.pixelpeppers.viewModels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixelpeppers.models.CreateReview
import com.example.pixelpeppers.models.UpdateReview
import com.example.pixelpeppers.repositories.ImageRepository
import com.example.pixelpeppers.repositories.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel
@Inject constructor(
    private val repository: ReviewRepository,
    private val imageRepository: ImageRepository,
) : ViewModel() {

    fun refreshReviewsByGameId(gameId: Int) {
        viewModelScope.launch {
            repository.refreshReviewsByGameId(gameId)
        }
    }

    fun refreshReviewsByUserId(userId: String) {
        viewModelScope.launch {
            repository.refreshReviewsByUserId(userId)
        }
    }

    fun getReviewsByGameId(gameId: Int) = repository.getReviewsByGameId(gameId)

    fun getReviewsByUserId(userId: String?) = repository.getReviewsByUserId(userId)

    fun deleteReview(reviewId: String) {
        viewModelScope.launch {
            repository.deleteReview(reviewId)
        }
    }

    fun addReview(review: CreateReview, imageUris: List<Uri> = emptyList()) {
        viewModelScope.launch {
            val ids = mutableListOf<String>()
            for (uri in imageUris) {
                ids.add(imageRepository.createImage(uri))
            }
            repository.addReview(review.copy(imageIDs = review.imageIDs + ids))
        }
    }


    fun updateReview(reviewId: String, review: UpdateReview, imageUris: List<Uri> = emptyList()) {
        viewModelScope.launch {
            val ids = mutableListOf<String>()
            for (uri in imageUris) {
                ids.add(imageRepository.createImage(uri))
            }
            repository.updateReview(reviewId, review.copy(imageIDs = review.imageIDs + ids))
        }
    }
}