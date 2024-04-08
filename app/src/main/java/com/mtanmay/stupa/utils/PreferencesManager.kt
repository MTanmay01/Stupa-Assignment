package com.mtanmay.stupa.utils

import android.content.Context
import androidx.core.content.edit

class PreferencesManager(context: Context) {

    private val sharedPref =
        context.getSharedPreferences("default_pref", Context.MODE_PRIVATE)

    fun loggedInUserToken(): Int =
        sharedPref.getInt(USER_TOKEN, -1)

    fun isUserLoggedIn(): Boolean {
        return loggedInUserToken() != -1
    }

    fun saveUserToken(token: Int) {
        sharedPref.edit {
            putInt(USER_TOKEN, token)
            apply()
        }
    }

    fun removeUserToken() {
        sharedPref.edit {
            remove(USER_TOKEN)
            apply()
        }
    }

}

private const val USER_TOKEN = "USER_TOKEN"