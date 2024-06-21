package com.example.pixelpeppers.models

data class UpdateReview(
    val rating: Int,
    val title: String,
    val description: String? = null,
    val imageIDs: List<String>? = null,
)
