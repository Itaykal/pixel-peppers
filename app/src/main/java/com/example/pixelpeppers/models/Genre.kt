package com.example.pixelpeppers.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "genres")
data class Genre(
    @PrimaryKey @SerializedName("name") val name: String,
)
