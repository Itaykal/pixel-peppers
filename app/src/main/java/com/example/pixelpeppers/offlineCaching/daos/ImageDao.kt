package com.example.pixelpeppers.offlineCaching.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pixelpeppers.models.Image

@Dao
interface ImageDao {
    @Query("SELECT * FROM images WHERE url = :imageUrl")
    fun getImageByUrl(imageUrl: String): Image

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImage(image: Image)
}