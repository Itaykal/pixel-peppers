package com.example.pixelpeppers.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Review (
    @PrimaryKey val id: String,
    @ColumnInfo(name="rating") val rating: Int,
    @ColumnInfo(name="title") val title: String,
    @ColumnInfo(name="description") val description: String? = null,
    @ColumnInfo(name="game_id") val gameId: Int,
    @ColumnInfo(name="author_id") val authorId: String,
    @ColumnInfo(name="author_display_name") val authorDisplayName: String,
    @ColumnInfo(name="media_urls") val mediaURLs: List<String>? = null,
)
