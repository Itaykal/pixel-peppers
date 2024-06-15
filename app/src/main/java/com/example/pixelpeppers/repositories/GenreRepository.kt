package com.example.pixelpeppers.repositories

import androidx.lifecycle.LiveData
import com.example.pixelpeppers.models.Genre
import com.example.pixelpeppers.offlineCaching.daos.GenreDao
import com.example.pixelpeppers.services.GamesService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GenreRepository(private val genreDao: GenreDao) {
    val genres: LiveData<List<Genre>> = genreDao.getGenres()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    suspend fun refreshGenres(limit: Int = 18) {
        println("Refreshing genres")
        try {
            val response = GamesService.instance.getGenres(limit)
            val genres: List<Genre> = Gson().fromJson(
                response.body?.charStream(), object : TypeToken<List<Genre>>() {}.type
            )
            if (genres.isNotEmpty()) {
                coroutineScope.launch(Dispatchers.IO) {
                    genreDao.insertAll(genres)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}