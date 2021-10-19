package com.example.selfcare.data

import android.content.Context
import android.widget.Toast
import androidx.datastore.preferences.core.clear
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsDataStore(context: Context) {

    private val dataStore = context.createDataStore(name = "user_prefs")

    companion object {
        val DARK_MODE_KEY = preferencesKey<Boolean>("DARK_MODE")
    }

    //Store user data
    suspend fun storeUser(isDarkMode: Boolean) {
        dataStore.edit {
            it[DARK_MODE_KEY] = isDarkMode
        }
    }

    val darkModeFlow: Flow<Boolean> = dataStore.data.map {
        it[DARK_MODE_KEY] ?: false
    }

    suspend fun deleteSettingsData(){
        dataStore.edit{
            it.clear()
        }
    }
}