package com.example.pixelpeppers.offlineCaching.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pixelpeppers.models.Review

@Dao
interface ReviewDao {
    @Query("SELECT * FROM reviews WHERE game_id = :gameId ORDER BY created_at DESC")
    fun getReviewsByGame(gameId: Int): LiveData<List<Review>>

    @Query("SELECT * FROM reviews WHERE author_id = :userId ORDER BY created_at DESC")
    fun getReviewsByUserID(userId: String): LiveData<List<Review>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(reviews: List<Review>)

    @Query("DELETE FROM reviews WHERE id = :reviewId")
    fun deleteReview(reviewId: String)

    @Query(
        "UPDATE reviews SET " +
                "rating = :rating, " +
                "title = :title, " +
                "description = :description " +
                "WHERE id = :reviewId"
    )
    fun updateReview(reviewId: String, rating: Int, title: String, description: String?)
}