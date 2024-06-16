package com.example.pixelpeppers.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "games")
data class Game(
    @PrimaryKey @SerializedName("id") val id: Int,
    @ColumnInfo(name = "name") @SerializedName("name") val name: String,
    @ColumnInfo(name = "cover") @SerializedName("cover") val cover: Cover? = null,
    @ColumnInfo(name = "genres") @SerializedName("genres") val genres: List<Genre>,
)
