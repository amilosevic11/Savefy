package com.rma.savefy.ui.mainscreen

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rma.savefy.data.sharedprefs.SharedPrefsManager
import com.rma.savefy.helpers.EXPENSE
import com.rma.savefy.models.Results
import com.rma.savefy.repos.FirebaseCloudStorageRepository
import com.rma.savefy.repos.FirebaseAuthRepository
import com.rma.savefy.repos.FirestoreRepository
import kotlinx.coroutines.launch
import kotlin.math.exp

class MainFragmentViewModel(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val firebaseCloudStorageRepository: FirebaseCloudStorageRepository,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    private val _isUserSignedOut: MutableLiveData<Boolean> = MutableLiveData()
    val isUserSignedOut: LiveData<Boolean>
        get() = _isUserSignedOut

    private val _userAvatar: MutableLiveData<Uri> = MutableLiveData()
    val userAvatar: LiveData<Uri>
        get() = _userAvatar

    private val _expensesAndRevenues: MutableLiveData<Pair<Double, Double>> = MutableLiveData()
    val expensesAndRevenues: LiveData<Pair<Double, Double>>
        get() = _expensesAndRevenues

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

    fun getAllData() {
        viewModelScope.launch {
            firestoreRepository.getAllData {
                filterData(it)
            }
        }
    }

    private fun filterData(data: List<Results>) {
        var expenses = 0.0
        var revenues = 0.0
        data.map {
            if(it.Type == EXPENSE) {
                expenses += it.Amount.toDouble()
            }
            else {
                revenues += it.Amount.toDouble()
            }
        }
        _expensesAndRevenues.postValue(Pair(expenses, revenues))
    }
}