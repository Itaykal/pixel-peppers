package com.example.pixelpeppers.clients

import com.example.pixelpeppers.extensions.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

private const val BASE_URL = "https://api.igdb.com/v4"
private val PLAIN = "text/plain".toMediaType()

class GameClient(
    private val okHttpClient: OkHttpClient,
) {
    suspend fun getGenres(limit: Int = 18): Response = withContext(Dispatchers.IO) {
        val body = "limit $limit; fields name;".toRequestBody(PLAIN)
        val request = Request.Builder().url("$BASE_URL/genres").post(body).build()
        okHttpClient.newCall(request).await()
    }

    suspend fun searchGames(search: String = "", limit: Int = 8): Response =
        withContext(Dispatchers.IO) {
            var query = "limit $limit; fields name,id,genres.name,genres.id,cover.url;"
            if (search != "") {
                query += " search \"$search\";"
            }
            val body = query.toRequestBody(PLAIN)
            val request = Request.Builder().url("$BASE_URL/games").post(body).build()
            okHttpClient.newCall(request).await()
        }

    suspend fun getGamesByGenre(genre: String, limit: Int =8): Response = withContext(Dispatchers.IO) {
        val query = "where genre.name=\"$genre\"; limit $limit; fields name,id,genres.name,genres.id,cover.url;"
        val body = query.toRequestBody(PLAIN)
        val request = Request.Builder().url("$BASE_URL/games").post(body).build()
        okHttpClient.newCall(request).await()
    }

    suspend fun getGame(gameId: Int): Response = withContext(Dispatchers.IO) {
        val query =
            "where id=$gameId; fields name,id,genres.name,genres.id,cover.url,summary,similar_games;"
        val body = query.toRequestBody(PLAIN)
        val request = Request.Builder().url("$BASE_URL/games").post(body).build()
        okHttpClient.newCall(request).await()
    }
}