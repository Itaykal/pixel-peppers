package com.example.pixelpeppers.repositories

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.util.UUID

class ImageRepository {
    private val storage = Firebase.storage
    private val storageRef = storage.reference
    private var imageRef: StorageReference = storageRef.child("images")

    suspend fun createImage(imageUri: Uri): String {
        val id = UUID.randomUUID().toString()

        val newImageRef = imageRef.child(id)
        newImageRef.putFile(imageUri).await()
        return id
    }

    fun getImage(id: String): StorageReference {
        return imageRef.child(id)
    }

    fun getImagesByIds(ids: List<String>): List<StorageReference> {
        return ids.map{ getImage(it) }
    }
}