package com.example.selfcare.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.selfcare.data.local.SettingsDataStoreNew.PreferenceKey.DARK_MODE_KEY
import com.example.selfcare.data.local.SettingsDataStoreNew.PreferenceKey.FIRST_TIME_KEY
import com.example.selfcare.data.local.SettingsDataStoreNew.PreferenceKey.NOTIF_MODE_KEY
import com.example.selfcare.data.local.SettingsDataStoreNew.PreferenceKey.USERNAME_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@JvmSuppressWildcards
@Singleton
class SettingsDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsDataStoreNew {
    companion object {
        const val SETTINGS_DATA_STORE_NAME = "settingsStore"
    }

    override val isFirstTime: Flow<Boolean>
    get() = dataStore.data.map{
        it[FIRST_TIME_KEY] ?: false
    }

    override val isDarkMode: Flow<Boolean>
    get() = dataStore.data.map{
        it[DARK_MODE_KEY] ?: false
    }

    override val isNotifMode: Flow<Boolean>
    get() = dataStore.data.map{
        it[NOTIF_MODE_KEY] ?: true
    }

    override val username: Flow<String>
    get() = dataStore.data.map{
        it[USERNAME_KEY] ?: "MATE"
    }

    override suspend fun storeDarkMode(isDarkMode: Boolean) {
        dataStore.edit {
            it[DARK_MODE_KEY] = isDarkMode
        }
    }

    override suspend fun storeFirstTime(isFirstTime: Boolean) {
        dataStore.edit {
            it[FIRST_TIME_KEY] = isFirstTime
        }
    }

    override suspend fun storeNotifMode(isNotifMode: Boolean) {
        dataStore.edit {
            it[NOTIF_MODE_KEY] = isNotifMode
        }
    }

    override suspend fun storeUsername(username: String) {
        dataStore.edit {
            it[USERNAME_KEY] = username
        }
    }

    override suspend fun deleteSettingsData() {
        dataStore.edit{
            it.clear()
        }
    }



}