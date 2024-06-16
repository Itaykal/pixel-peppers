package com.example.pixelpeppers.models

data class CreateReview (
    val rating: Int,
    val title: String,
    val description: String? = null,
    val gameId: Int,
    val mediaURLs: List<String>? = null,
)
