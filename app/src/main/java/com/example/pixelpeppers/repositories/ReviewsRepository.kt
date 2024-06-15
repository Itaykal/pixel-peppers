package com.example.pixelpeppers.repositories

import com.example.pixelpeppers.models.CreateReview
import com.example.pixelpeppers.models.Review
import com.example.pixelpeppers.models.UpdateReview
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class ReviewsRepository private constructor() {
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    companion object {
        val instance: ReviewsRepository by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { ReviewsRepository() }
        private const val REVIEWS_COLLECTION = "reviews"
    }

    fun addReview(createReview: CreateReview, callback: (id: String) -> Unit) {
        val doc = db.collection(REVIEWS_COLLECTION).document()
        val currentUser = auth.currentUser!!
        val review = Review(
            id = doc.id,
            gameId = createReview.gameId,
            description = createReview.description,
            rating = createReview.rating,
            title = createReview.title,
            authorId = currentUser.uid,
            authorDisplayName = currentUser.displayName!!,
        )
        doc.set(review)
            .addOnCompleteListener {
                callback(review.id)
            }
    }

    fun deleteReview(reviewId: String, callback: () -> Unit) {
        db.collection(REVIEWS_COLLECTION)
            .document(reviewId)
            .delete()
            .addOnCompleteListener {
                callback()
            }
    }

    fun updateReview(reviewId: String, createReview: UpdateReview, callback: () -> Unit) {
        db.collection(REVIEWS_COLLECTION)
            .document(reviewId)
            .update(
                "description", createReview.description,
                "rating", createReview.rating,
                "title", createReview.title
            )
            .addOnCompleteListener {
                callback()
            }
    }

    fun getGamesReviews(gameId: Int, callback: (reviews: List<Review>) -> Unit) {
        db.collection(REVIEWS_COLLECTION)
            .whereEqualTo("gameId", gameId)
            .get()
            .addOnCompleteListener {
                val reviews = it.result.toObjects(Review::class.java)
                callback(reviews)
            }
    }


}