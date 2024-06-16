package com.example.pixelpeppers.models

data class CreateReview (
    val rating: Int,
    val title: String,
    val description: String? = null,
    val gameId: Int,
    val imageIDs: List<String>? = null,
)
