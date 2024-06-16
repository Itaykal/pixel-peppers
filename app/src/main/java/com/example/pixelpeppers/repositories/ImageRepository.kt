package com.example.pixelpeppers.repositories

import androidx.lifecycle.LiveData
import com.example.pixelpeppers.models.CreateImage
import com.example.pixelpeppers.models.Image
import com.example.pixelpeppers.offlineCaching.daos.ImageDao
import com.google.firebase.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest

class ImageRepository(private val imageDao: ImageDao) {
    val storage = Firebase.storage
    val storageRef = storage.reference
    var imageRef: StorageReference = storageRef.child("images")

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    @OptIn(ExperimentalStdlibApi::class)
    suspend fun createImage(image: CreateImage): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(image.bytes)
        val id = digest.toHexString()

        val newImageRef = imageRef.child(id)

        coroutineScope.launch(Dispatchers.IO) {
            newImageRef.putBytes(image.bytes).await()
        }

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