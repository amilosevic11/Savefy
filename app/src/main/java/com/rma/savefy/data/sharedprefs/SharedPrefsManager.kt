package com.rma.savefy.data.sharedprefs

import android.content.Context
import com.rma.savefy.SavefyApp
import com.rma.savefy.helpers.EMPTY_STRING
import com.rma.savefy.helpers.SHARED_PREFS_NAME
import com.rma.savefy.helpers.USER_ID

class SharedPrefsManager {
    private val sharedPrefs = SavefyApp.application.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    private val userEditor = sharedPrefs.edit()

    fun saveUserId(id: String) {
        userEditor.putString(USER_ID, id)
        userEditor.apply()
    }

    fun getUserId() = sharedPrefs.getString(USER_ID, EMPTY_STRING)
}