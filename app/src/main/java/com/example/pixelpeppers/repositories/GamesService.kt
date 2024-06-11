package com.example.pixelpeppers.repositories

import com.example.pixelpeppers.models.Game
import com.example.pixelpeppers.models.Genre
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody


private object RequestInterceptor : Interceptor {
    private const val AUTHORIZATION_HEADER = "Authorization"
    private const val CLIENT_ID = "Client-ID"

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        println("Outgoing request to ${request.url}")
        val newRequest = request.newBuilder().addHeader("accept", "application/json")
            .addHeader(CLIENT_ID, "gvp0touvixyjwkce1yi3z37d1w8203")
            .addHeader(AUTHORIZATION_HEADER, "Bearer <AUTH>").build()
        return chain.proceed(newRequest)
    }
}

object GamesService {
    private const val BASE_URL = "https://api.igdb.com/v4"
    private val PLAIN = "text/plain".toMediaType();
    private val client = OkHttpClient().newBuilder().addInterceptor(RequestInterceptor).build()

    fun getGenres(limit: Int = 18): List<Genre> {
        val body = "limit $limit; fields name;".toRequestBody(PLAIN)
        val request = Request.Builder().url("$BASE_URL/genres").post(body).build()
        val response = client.newCall(request).execute()
        return Gson().fromJson(
            response.body?.charStream(), object : TypeToken<List<Genre>>() {}.type
        )
    }

    fun searchGames(search: String = "", limit: Int = 8): List<Game> {
        var query = "limit $limit; fields name,id;"
        if (search != "") {
            query += " search \"$search\";"
        }
        val body = query.toRequestBody(PLAIN)
        val request = Request.Builder().url("$BASE_URL/games").post(body).build()
        val response = client.newCall(request).execute()
        return Gson().fromJson(
            response.body?.charStream(), object : TypeToken<List<Game>>() {}.type
        )

    }


    fun getCoverURL(gameId: Int): String {
        var query = "where game=$gameId; fields url;"
        val body = query.toRequestBody(PLAIN)
        val request = Request.Builder().url("$BASE_URL/covers").post(body).build()
        val response = client.newCall(request).execute()
        return JsonParser().parse(response.body?.charStream()).asJsonArray[0].asJsonObject.get("url").asString
    }
}