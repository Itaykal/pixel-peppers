package com.example.pixelpeppers.offlineCaching

import androidx.room.TypeConverter
import com.example.pixelpeppers.models.Genre

class Converters {
    @TypeConverter
    fun genreFromString(value: String): Genre {
        return Genre(value)
    }

    @TypeConverter
    fun genreToString(genre: Genre): String {
        return genre.name
    }

//    @TypeConverter
//    fun coverFromString(value: String): Cover {
//        return Cover(value)
//    }
//
//    @TypeConverter
//    fun coverToString(cover: Cover): String {
//        return cover.url
//    }

    @TypeConverter
    fun listOfStringsToString(l: List<String>): String {
        return l.joinToString(",")
    }

    @TypeConverter
    fun listOfStringsFromString(value: String): List<String> {
        return value.split(",")
    }
}