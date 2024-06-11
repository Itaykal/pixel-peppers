package com.example.pixelpeppers.models

import com.google.gson.annotations.SerializedName

data class Game(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("posterURL") val posterURL: String,
    @SerializedName("genres") val genres: List<Genre>
)
