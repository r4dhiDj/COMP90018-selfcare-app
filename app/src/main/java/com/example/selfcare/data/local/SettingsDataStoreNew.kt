package com.example.selfcare.data.local


import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow

/**
 * COMP90018 - SelfCare
 * [SettingsDataStoreNew] retains personalisation settings
 */

interface SettingsDataStoreNew {
    val isFirstTime: Flow<Boolean>
    val isDarkMode: Flow<Boolean>
    val isNotifMode: Flow<Boolean>
    val username: Flow<String>

    suspend fun storeFirstTime(isFirstTime: Boolean)
    suspend fun storeDarkMode(isDarkMode: Boolean)
    suspend fun storeNotifMode(isNotifMode: Boolean)
    suspend fun storeUsername(username: String)
    suspend fun deleteSettingsData()

    object PreferenceKey {
        val FIRST_TIME_KEY = booleanPreferencesKey("SETTINGS_FIRST_TIME")
        val DARK_MODE_KEY = booleanPreferencesKey("SETTINGS_DARK_MODE")
        val NOTIF_MODE_KEY = booleanPreferencesKey("SETTINGS_NOTIF_MODE")
        val USERNAME_KEY = stringPreferencesKey("SETTINGS_USERNAME")
    }

    companion object {
        const val SETTINGS_FIRST_TIME = "isFirstTime"
        const val SETTINGS_DARK_MODE = "isDarkMode"
        const val SETTINGS_NOTIF_MODE = "isNotifMode"
        const val SETTINGS_USERNAME = "username"
    }

}