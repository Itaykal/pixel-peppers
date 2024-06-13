package com.example.pixelpeppers.repositories

import com.example.pixelpeppers.clients.GamesClient
import com.example.pixelpeppers.models.Game
import com.example.pixelpeppers.models.Genre
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GamesRepository private constructor() {
    companion object {
        val instance: GamesRepository by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { GamesRepository() }
    }

    fun getGenres(limit: Int = 18, callback: (genres: List<Genre>) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = GamesClient.getGenres(limit)
                val genres: List<Genre> = Gson().fromJson(
                    response.body?.charStream(), object : TypeToken<List<Genre>>() {}.type
                )
                callback(genres)
            } catch (e: Exception) {
                // TODO: Handle the error here
                e.printStackTrace()
            }
        }
    }

    fun searchGames(search: String = "", limit: Int = 8, callback: (games: List<Game>) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = GamesClient.searchGames(search, limit)
                val games: List<Game> = Gson().fromJson(
                    response.body?.charStream(), object : TypeToken<List<Game>>() {}.type
                )
                callback(games)
            } catch (e: Exception) {
                // TODO: Handle the error here
                e.printStackTrace()
            }
        }
    }

    fun getGame(gameId: Int, callback: (game: Game) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = GamesClient.getGame(gameId)
                val games: List<Game> = Gson().fromJson(
                    response.body?.charStream(), object : TypeToken<List<Game>>() {}.type
                )
                callback(games[0])
            } catch (e: Exception) {
                // TODO: Handle the error here
                e.printStackTrace()
            }
        }
    }

    fun getCoverURL(gameId: Int, callback: (url: String) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = GamesClient.getCoverURL(gameId)
                val url =
                    JsonParser().parse(response.body?.charStream()).asJsonArray[0].asJsonObject.get(
                        "url"
                    ).asString
                callback(url)
            } catch (e: Exception) {
                // TODO: Handle the error here
                e.printStackTrace()
            }
        }
    }
}