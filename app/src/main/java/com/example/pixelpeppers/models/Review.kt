package com.example.pixelpeppers.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class Review (
    @PrimaryKey val id: String = "",
    @ColumnInfo(name="rating") val rating: Int = 1,
    @ColumnInfo(name="title") val title: String = "",
    @ColumnInfo(name="description") val description: String? = null,
    @ColumnInfo(name="game_id") val gameId: Int = 0,
    @ColumnInfo(name="author_id") val authorId: String = "",
    @ColumnInfo(name="image_ids") val imageIDs: List<String>? = null,
    @ColumnInfo(name="created_at") val createdAt: Long = 0,
)

