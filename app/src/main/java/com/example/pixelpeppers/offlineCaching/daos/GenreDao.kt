package com.example.pixelpeppers.offlineCaching.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pixelpeppers.models.Genre

@Dao
interface GenreDao {
    @Query("select * from genres")
    fun getGenres(): LiveData<List<Genre>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(genres: List<Genre>)
}