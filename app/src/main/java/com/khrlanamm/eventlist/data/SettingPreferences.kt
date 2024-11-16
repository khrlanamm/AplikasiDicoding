package com.khrlanamm.eventlist.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class SettingsPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    fun getDarkMode() = dataStore.data.map { it[DARK_MODE_PREFERENCES] == true }

    suspend fun setDarkMode(darkMode: Boolean) {
        dataStore.edit {
            it[DARK_MODE_PREFERENCES] = darkMode
        }
    }

    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
        private val DARK_MODE_PREFERENCES = booleanPreferencesKey("dark_mode_preferences")

        @Volatile
        private var INSTANCE: SettingsPreferences? = null

        @JvmStatic
        fun getInstance(dataStore: DataStore<Preferences>) =
            INSTANCE ?: synchronized(this) {
                val instance = SettingsPreferences(dataStore)
                INSTANCE = instance
                instance
            }
    }
}