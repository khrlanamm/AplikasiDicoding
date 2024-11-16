package com.khrlanamm.eventlist.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.khrlanamm.eventlist.data.SettingsPreferences
import com.khrlanamm.eventlist.data.SettingsPreferences.Companion.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel private constructor(
    private val settingsPreferences: SettingsPreferences
) :
    ViewModel() {

    fun getDarkMode() = settingsPreferences.getDarkMode()

    fun setDarkMode(darkMode: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsPreferences.setDarkMode(darkMode)
        }
    }

    class Factory(private val context: Context) :
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SettingsViewModel(SettingsPreferences.getInstance(context.dataStore)) as T
        }
    }

    companion object {
        fun getInstance(context: Context) = Factory(context)
    }
}