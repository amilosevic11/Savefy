package com.rma.savefy.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rma.savefy.repos.FirebaseAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val firebaseAuthRepository: FirebaseAuthRepository) : ViewModel() {

    private val _isUserSignedIn: MutableLiveData<Boolean> = MutableLiveData()
    val isUserSignedIn: LiveData<Boolean>
        get() = _isUserSignedIn

    fun signIn(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isUserSignedIn.postValue(firebaseAuthRepository.authenticateUserWithEmail(email, password))
        }
    }
}