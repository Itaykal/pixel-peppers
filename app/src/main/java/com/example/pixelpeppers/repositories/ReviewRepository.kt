package com.example.pixelpeppers.repositories

import androidx.lifecycle.LiveData
import com.example.pixelpeppers.models.CreateReview
import com.example.pixelpeppers.models.Review
import com.example.pixelpeppers.models.UpdateReview
import com.example.pixelpeppers.offlineCaching.daos.ReviewDao
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ReviewRepository(private val reviewDao: ReviewDao) {

    private val auth = Firebase.auth
    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    companion object {
        private const val REVIEWS_COLLECTION = "reviews"
        private val collection = Firebase.firestore.collection(REVIEWS_COLLECTION)
    }

    suspend fun addReview(createReview: CreateReview) {
        val doc = collection.document()
        val currentUser = auth.currentUser!!
        val review = Review(
            id = doc.id,
            gameId = createReview.gameId,
            description = createReview.description,
            rating = createReview.rating,
            title = createReview.title,
            authorId = currentUser.uid,
            authorDisplayName = currentUser.displayName!!,
            imageIDs = createReview.imageIDs,
        )
        doc.set(review).await()
        println("Review added: $review")
        coroutineScope.launch(Dispatchers.IO) {
            reviewDao.insertAll(listOf(review))
        }
    }

    suspend fun deleteReview(reviewId: String) {
        collection
            .document(reviewId)
            .delete()
            .await()
        coroutineScope.launch(Dispatchers.IO) {
            reviewDao.deleteReview(reviewId)
        }
    }

    suspend fun updateReview(reviewId: String, updateReview: UpdateReview) {
        collection
            .document(reviewId)
            .update(
                "description", updateReview.description,
                "rating", updateReview.rating,
                "title", updateReview.title
            )
            .await()
        coroutineScope.launch(Dispatchers.IO) {
            reviewDao.updateReview(
                reviewId,
                updateReview.rating,
                updateReview.title,
                updateReview.description,
            )
        }
    }

    fun getReviewsByGameId(gameId: Int): LiveData<List<Review>> {
        return reviewDao.getReviewsByGame(gameId)
    }

    suspend fun refreshReviewsByGameId(gameId: Int) {
        try {
            val reviews = collection
                .whereEqualTo("gameId", gameId)
                .get()
                .await()
                .toObjects(Review::class.java)
            coroutineScope.launch(Dispatchers.IO) {
                reviewDao.insertAll(reviews)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}