package com.rma.savefy.ui.revenuesandexpenses

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rma.savefy.data.sharedprefs.SharedPrefsManager
import com.rma.savefy.repos.FirebaseCloudStorageRepository
import com.rma.savefy.repos.FirebaseAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RevenuesAndExpensesViewModel(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val firebaseCloudStorageRepository: FirebaseCloudStorageRepository
) : ViewModel() {

    private val _isUserSignedOut: MutableLiveData<Boolean> = MutableLiveData()
    val isUserSignedOut: LiveData<Boolean>
        get() = _isUserSignedOut

    private val _userAvatar: MutableLiveData<Uri> = MutableLiveData()
    val userAvatar: LiveData<Uri>
        get() = _userAvatar

    fun signOut() {
        firebaseAuthRepository.signOut()
        SharedPrefsManager().saveUserId("")
        _isUserSignedOut.postValue(true)
    }

    fun uploadAvatar(photoUri: Uri) {
        viewModelScope.launch {
            firebaseCloudStorageRepository.uploadPhoto(photoUri)
        }
    }

    fun downloadAvatar() {
        viewModelScope.launch {
            firebaseCloudStorageRepository.downloadPhoto {
                _userAvatar.postValue(it)
            }
        }
    }
}