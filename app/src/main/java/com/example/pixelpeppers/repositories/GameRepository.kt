package com.example.pixelpeppers.repositories

import com.example.pixelpeppers.models.Game
import com.example.pixelpeppers.models.Genre
import com.example.pixelpeppers.services.GamesService
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken

class GameRepository private constructor() {
    companion object {
        val instance: GameRepository by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { GameRepository() }
    }

    suspend fun searchGames(search: String = "", limit: Int = 8): List<Game> {
        try {
            val response = GamesService.instance.searchGames(search, limit)
            return Gson().fromJson(
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
            val response = GamesService.instance.getGame(gameId)
            val games: List<Game> = Gson().fromJson(
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
            val response = GamesService.instance.getCoverURL(gameId)
            return JsonParser.parseReader(response.body?.charStream())
                .asJsonArray[0].asJsonObject.get(
                "url"
            ).asString
        } catch (e: Exception) {
            // TODO: Handle the error here
            e.printStackTrace()
            return ""
        }
    }
}