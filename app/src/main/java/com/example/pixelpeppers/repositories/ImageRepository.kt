package com.example.pixelpeppers.repositories

import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.pixelpeppers.models.Image
import com.example.pixelpeppers.offlineCaching.daos.ImageDao
import com.google.firebase.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class ImageRepository(private val imageDao: ImageDao) {
    private val storage = Firebase.storage
    private val storageRef = storage.reference
    private var imageRef: StorageReference = storageRef.child("images")

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    suspend fun createImage(imageUri: Uri): String {
        val id = UUID.randomUUID().toString()

        val newImageRef = imageRef.child(id)
        coroutineScope.launch(Dispatchers.IO) {
            newImageRef.putFile(imageUri).await()
            refreshImage(id)
        }
        refreshImage(id)
        return id
    }

    suspend fun refreshImage(id: String) {
        val HUNDRED_MEGABYTES: Long = 100 * 1024 * 1024

        val targetImageRef = imageRef.child(id)

        coroutineScope.launch(Dispatchers.IO) {
            try {
                val bytes = targetImageRef.getBytes(HUNDRED_MEGABYTES).await()
                val image = Image(id, bytes)
                imageDao.insertImage(image)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun refreshImages(ids: List<String>) {
        for (id in ids) {
            refreshImage(id)
        }
    }

    fun getImage(id: String): LiveData<Image> = imageDao.getImageById(id)
    fun getImagesByIds(ids: List<String>): LiveData<List<Image>> = imageDao.getImagesByIds(ids)
}