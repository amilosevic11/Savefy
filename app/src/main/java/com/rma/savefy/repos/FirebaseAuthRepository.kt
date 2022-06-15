package com.rma.savefy.repos

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository(private val firebaseAuth: FirebaseAuth) {

    suspend fun authenticateUserWithEmail(email: String, password: String): Boolean {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            it.user
        }.await()
        return true
    }
}