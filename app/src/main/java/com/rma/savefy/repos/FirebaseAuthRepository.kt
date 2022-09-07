package com.rma.savefy.repos

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.rma.savefy.R
import com.rma.savefy.SavefyApp
import com.rma.savefy.data.sharedprefs.SharedPrefsManager
import com.rma.savefy.helpers.makeToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.concurrent.Flow

class FirebaseAuthRepository(private val firebaseAuth: FirebaseAuth) {

    suspend fun authenticateUserWithEmail(email: String, password: String, onResult: (Boolean) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        onResult(true)
                        it.user?.let { firebaseUser ->
                            SharedPrefsManager().saveUserId(firebaseUser.uid)
                        }
                    }
                    .addOnFailureListener {
                        onResult(false)
                        makeToast(it.message.toString(), lengthLong = false)
                    }.await()
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    suspend fun createNewUserWithEmailAndPassword(email: String, password: String, onResult: (Boolean) -> Unit) {
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    makeToast(SavefyApp.application.getString(R.string.registration_successful), lengthLong = false)
                    onResult(true)
                }
                .addOnFailureListener {
                    makeToast(it.message.toString(), lengthLong = false)
                    onResult(false)
                }.await()
        } catch (e: Exception) {
            onResult(false)
        }
    }

    fun getCurrentUser(): FirebaseUser?  {
        return firebaseAuth.currentUser
    }

    fun signOut() {
        firebaseAuth.signOut()
    }
}