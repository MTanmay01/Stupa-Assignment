package com.mtanmay.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreManager {
    suspend fun saveSignedInToken(datastore: DataStore<Preferences>, token: String) {
        datastore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    suspend fun removeSignedInToken(datastore: DataStore<Preferences>) {
        datastore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
        }
    }

    private val TOKEN_KEY = stringPreferencesKey("SIGNED_IN_USER_TOKEN")
}