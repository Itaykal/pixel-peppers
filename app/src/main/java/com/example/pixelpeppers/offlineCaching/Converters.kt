package com.example.pixelpeppers.offlineCaching

import androidx.room.TypeConverter
import com.example.pixelpeppers.models.Genre
import com.example.pixelpeppers.models.Image

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
    fun listOfImagesToString(l: List<Image>): String {
        return l.joinToString(",") { it.url }
    }

    @TypeConverter
    fun listOfImagesFromString(value: String): List<Image> {
        return value.split(",").map { Image(it) }
    }
}