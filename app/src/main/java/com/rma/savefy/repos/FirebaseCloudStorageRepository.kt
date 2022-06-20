package com.rma.savefy.repos

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.rma.savefy.R
import com.rma.savefy.SavefyApp
import com.rma.savefy.data.sharedprefs.SharedPrefsManager
import com.rma.savefy.helpers.makeToast
import kotlinx.coroutines.tasks.await

class FirebaseCloudStorageRepository(firebaseCloudStorage: FirebaseStorage) {

    private val storage = firebaseCloudStorage.reference

    suspend fun uploadPhoto(imageUri: Uri) {
        val imageRef = storage.child("${SharedPrefsManager().getUserId()}.jpg")
        try {
            imageRef.putFile(imageUri).addOnSuccessListener {
                makeToast(SavefyApp.application.getString(R.string.image_upload_success), lengthLong = false)
            }.await()
        } catch (e: Exception) {
            makeToast(e.message.toString(), lengthLong = false)
        }
    }

    suspend fun downloadPhoto() : Uri {
        var photoUri = Uri.EMPTY
        try {
            storage.child("${SharedPrefsManager().getUserId()}.jpg").downloadUrl.addOnSuccessListener {
                photoUri = it
            }.addOnFailureListener {
                makeToast(it.message.toString(), lengthLong = false)
            }.await()
            return photoUri
        } catch (e: Exception) {
            makeToast(e.message.toString(), lengthLong = false)
        }
        return photoUri
    }
}