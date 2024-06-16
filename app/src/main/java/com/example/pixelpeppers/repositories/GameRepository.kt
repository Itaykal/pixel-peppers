package com.example.pixelpeppers.repositories

import androidx.lifecycle.LiveData
import com.example.pixelpeppers.clients.GameClient
import com.example.pixelpeppers.models.Game
import com.example.pixelpeppers.offlineCaching.daos.GameDao
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameRepository(
    private val gameDao: GameDao,
    private val gson: Gson,
    private val gameClient: GameClient,
) {
    val allGames: LiveData<List<Game>> = gameDao.getAllGames()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    suspend fun getGame(gameId: Int): Game? {
        try {
            val response = gameClient.getGame(gameId)
            val games: List<Game> = gson.fromJson(
                response.body?.charStream(), object : TypeToken<List<Game>>() {}.type
            )
            return games[0]
        } catch (e: Exception) {
            // TODO: Handle the error here
            e.printStackTrace()
            return null
        }
    }

    suspend fun refreshGameByID(gameId: Int) {
        try {
            val response = gameClient.getGame(gameId)
            val games: List<Game> = gson.fromJson(
                response.body?.charStream(), object : TypeToken<List<Game>>() {}.type
            )
            coroutineScope.launch(Dispatchers.IO) {
                gameDao.insertAll(games)
            }
        } catch (e: Exception) {
            // TODO: Handle the error here
            e.printStackTrace()
        }
    }

    fun getGameById(gameId: Int): LiveData<Game> {
        return gameDao.getGameById(gameId)
    }

    suspend fun refreshGamesByGenre(genre: String) {
        try {
            val response = gameClient.getGamesByGenre(genre)
            val games: List<Game> = gson.fromJson(
                response.body?.charStream(), object : TypeToken<List<Game>>() {}.type
            )
            coroutineScope.launch(Dispatchers.IO) {
                gameDao.insertAll(games)
            }

        } catch (e: Exception) {
            // TODO: Handle the error here
            e.printStackTrace()
        }
    }

    fun getGamesByGenre(genre: String): LiveData<List<Game>> {
        return gameDao.getGamesByGenre(genre)
    }

    suspend fun refreshGamesBySearch(query: String) {
        try {
            val response = gameClient.searchGames(query)
            val games: List<Game> = gson.fromJson(
                response.body?.charStream(), object : TypeToken<List<Game>>() {}.type
            )
            coroutineScope.launch(Dispatchers.IO) {
                gameDao.insertAll(games)
            }

        } catch (e: Exception) {
            // TODO: Handle the error here
            e.printStackTrace()
        }
    }

    fun getGamesBySearch(query: String): LiveData<List<Game>> {
        return gameDao.getGamesBySearch(query)
    }
}