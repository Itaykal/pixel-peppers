package com.example.pixelpeppers.offlineCaching.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pixelpeppers.models.Image

@Dao
interface ImageDao {
    @Query("SELECT * FROM images WHERE id = :id")
    fun getImageById(id: String): LiveData<Image>

    @Query("SELECT * FROM images WHERE id IN (:ids)")
    fun getImagesByIds(ids: List<String>): LiveData<List<Image>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImage(image: Image)
}