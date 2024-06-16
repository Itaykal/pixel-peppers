package com.example.pixelpeppers.offlineCaching.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pixelpeppers.models.Game
import com.example.pixelpeppers.models.Genre

@Dao
interface GameDao {
    @Query("select * from games")
    fun getAllGames(): LiveData<List<Game>>

    @Query("select * from games where id = :id")
    fun getGameById(id: Int): LiveData<Game>
    @Query("select * from games where :genre in genres")
    fun getGamesByGenre(genre: String): LiveData<List<Game>>

    @Query("select * from games where name like '%' || :query || '%'")
    fun getGamesBySearch(query: String): LiveData<List<Game>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(genres: List<Game>)
}