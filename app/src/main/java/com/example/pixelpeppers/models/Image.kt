package com.example.pixelpeppers.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class Image (
    @PrimaryKey val id: String,
    @ColumnInfo(name = "bytes") val bytes: ByteArray? = null
)