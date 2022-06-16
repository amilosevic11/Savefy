package com.rma.savefy.helpers

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import com.rma.savefy.R
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

fun initPopupMenu(anchor: View, context: Context, listener: PopupMenu.OnMenuItemClickListener) {
    val popup = PopupMenu(context, anchor)
    val inflater = popup.menuInflater
    inflater.inflate(R.menu.actions, popup.menu)
    popup.setOnMenuItemClickListener(listener)
    popup.show()
}