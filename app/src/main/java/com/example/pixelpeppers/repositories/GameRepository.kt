package com.example.pixelpeppers.repositories

import com.example.pixelpeppers.models.Game
import com.example.pixelpeppers.offlineCaching.daos.GameDao
import com.example.pixelpeppers.clients.GameClient
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken

class GameRepository(
    private val gameDao: GameDao,
    private val gson: Gson,
    private val gameClient: GameClient,
) {

    suspend fun searchGames(search: String = "", limit: Int = 8): List<Game> {
        try {
            val response = gameClient.searchGames(search, limit)
            return gson.fromJson(
                response.body?.charStream(), object : TypeToken<List<Game>>() {}.type
            )
        } catch (e: Exception) {
            // TODO: Handle the error here
            e.printStackTrace()
            return emptyList()
        }
    }

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

    suspend fun getCoverURL(gameId: Int): String {
        try {
            val response = gameClient.getCoverURL(gameId)
            return gson.fromJson(
                response.body?.charStream(),
                JsonArray::class.java
            )[0].asJsonObject.get(
                "url"
            ).asString
        } catch (e: Exception) {
            // TODO: Handle the error here
            e.printStackTrace()
            return ""
        }
    }
}