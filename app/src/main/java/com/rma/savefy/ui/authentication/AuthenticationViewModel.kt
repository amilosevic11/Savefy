package com.rma.savefy.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.rma.savefy.data.SharedPrefsManager
import com.rma.savefy.repos.FirebaseAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val firebaseAuthRepository: FirebaseAuthRepository) : ViewModel() {

    private val _isUserSignedIn: MutableLiveData<Boolean> = MutableLiveData()
    val isUserSignedIn: LiveData<Boolean>
        get() = _isUserSignedIn

    private val _currentUser: MutableLiveData<FirebaseUser> = MutableLiveData()
    val currentUser: LiveData<FirebaseUser>
        get() = _currentUser

    fun signIn(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val currentFirebaseUser = firebaseAuthRepository.authenticateUserWithEmail(email, password)

            if(currentFirebaseUser != null) {
                _isUserSignedIn.postValue(true)
                SharedPrefsManager().saveUserId(currentFirebaseUser.uid)
            }
            else {
                _isUserSignedIn.postValue(false)
            }
        }
    }

    fun isUserAlreadySignedIn() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentFirebaseUser = firebaseAuthRepository.getCurrentUser()

            if(currentFirebaseUser != null) {
                _isUserSignedIn.postValue(true)
                SharedPrefsManager().saveUserId(currentFirebaseUser.uid)
            }
            else {
                _isUserSignedIn.postValue(false)
            }
        }
    }

    fun signOut() {
        firebaseAuthRepository.signOut()
    }
}