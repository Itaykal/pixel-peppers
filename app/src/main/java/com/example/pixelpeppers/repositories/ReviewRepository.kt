package com.example.pixelpeppers.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import java.time.Instant

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
            imageIDs = createReview.imageIDs,
            createdAt = Instant.now().toEpochMilli(),
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

    fun getReviewsByUserId(userId: String?): LiveData<List<Review>> {
        if (userId == null) {
            return MutableLiveData(emptyList())
        }
        return reviewDao.getReviewsByUserID(userId)
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

    suspend fun refreshReviewsByUserId(userId: String) {
        try {
            val reviews = collection
                .whereEqualTo("userId", userId)
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