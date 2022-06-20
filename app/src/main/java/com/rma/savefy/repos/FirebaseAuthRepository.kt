package com.rma.savefy.repos

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.rma.savefy.R
import com.rma.savefy.SavefyApp
import com.rma.savefy.helpers.makeToast
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.concurrent.Flow

class FirebaseAuthRepository(private val firebaseAuth: FirebaseAuth) {

    suspend fun authenticateUserWithEmail(email: String, password: String) = flow {
        var currentUser: FirebaseUser? = null
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
//                    emit(it.user)
                }
                .addOnFailureListener {

                }.await()
        } catch (e: Exception) {
            makeToast(e.message.toString(), lengthLong = false)
        }
        emit(currentUser)
    }

//    suspend fun authenticateUserWithEmail(email: String, password: String): FirebaseUser? {
//        var currentUser: FirebaseUser? = null
//        try {
//            firebaseAuth.signInWithEmailAndPassword(email, password)
//                .addOnSuccessListener {
//                    makeToast(SavefyApp.application.getString(R.string.login_successful), lengthLong = false)
//                    currentUser = it.user
//                }
//                .addOnFailureListener {
////                    makeToast(SavefyApp.application.getString(R.string.login_failed), lengthLong = false)
//                }.await()
//        } catch (e: Exception) {
//            makeToast(e.message.toString())
//        }
//
//        return currentUser
//    }

    suspend fun createNewUserWithEmailAndPassword(email: String, password: String): Boolean {
        var isSuccessful = false
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    makeToast(SavefyApp.application.getString(R.string.registration_successful), lengthLong = false)
                    isSuccessful = true
                }
                .addOnFailureListener {
//                    makeToast(SavefyApp.application.getString(R.string.registration_failed), lengthLong = false)
                    isSuccessful = false
                }.await()
        } catch (e: Exception) {
            isSuccessful = false
            makeToast(e.message.toString(), lengthLong = false)
        }

        return isSuccessful
    }

    fun getCurrentUser(): FirebaseUser?  {
        return firebaseAuth.currentUser
    }

    fun signOut() {
        firebaseAuth.signOut()
    }
}