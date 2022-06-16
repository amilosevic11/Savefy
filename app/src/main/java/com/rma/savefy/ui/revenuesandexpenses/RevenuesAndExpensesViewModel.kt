package com.rma.savefy.ui.revenuesandexpenses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rma.savefy.data.SharedPrefsManager
import com.rma.savefy.repos.FirebaseAuthRepository

class RevenuesAndExpensesViewModel(private val firebaseAuthRepository: FirebaseAuthRepository) : ViewModel() {

    private val _isUserSignedOut: MutableLiveData<Boolean> = MutableLiveData()
    val isUserSignedOut: LiveData<Boolean>
        get() = _isUserSignedOut

    fun signOut() {
        firebaseAuthRepository.signOut()
        SharedPrefsManager().saveUserId("")
        _isUserSignedOut.postValue(true)
    }
}