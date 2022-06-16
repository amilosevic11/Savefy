package com.rma.savefy.repos

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository(private val firebaseAuth: FirebaseAuth) {

    suspend fun authenticateUserWithEmail(email: String, password: String): FirebaseUser? {
        var currentUser: FirebaseUser? = null
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                currentUser = it.user
            }.await()

        return currentUser
    }

    suspend fun createNewUserWithEmailAndPassword(email: String, password: String): Boolean {
        var isSuccessful = false
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                isSuccessful = true
            }
            .addOnFailureListener {
                isSuccessful = false
            }.await()

        return isSuccessful
    }

    fun getCurrentUser(): FirebaseUser?  {
        return firebaseAuth.currentUser
    }
}