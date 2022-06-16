package com.rma.savefy.helpers

import android.text.TextUtils
import android.widget.Toast
import com.rma.savefy.SavefyApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun makeToast(message: String, lengthLong: Boolean = false){
    CoroutineScope(Dispatchers.Main).launch {
        Toast.makeText(SavefyApp.application, message, if(lengthLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
    }
}

fun String.isEmailValid(): Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}