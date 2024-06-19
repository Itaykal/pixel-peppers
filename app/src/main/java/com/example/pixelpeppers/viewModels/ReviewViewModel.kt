package com.example.pixelpeppers.viewModels

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

    fun addReview(review: CreateReview) {
        viewModelScope.launch {
            repository.addReview(review)
        }
    }


    fun updateReview(reviewId: String, review: UpdateReview) {
        viewModelScope.launch {
            repository.updateReview(reviewId, review)
        }
    }
}