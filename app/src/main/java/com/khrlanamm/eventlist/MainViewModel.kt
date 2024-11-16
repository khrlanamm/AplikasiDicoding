package com.khrlanamm.eventlist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khrlanamm.eventlist.data.SettingsPreferences
import com.khrlanamm.eventlist.data.SettingsPreferences.Companion.dataStore

class MainViewModel private constructor(
    private val settingsPreferences: SettingsPreferences
) :
    ViewModel() {

    fun getDarkMode() = settingsPreferences.getDarkMode()

    class Factory(private val context: Context) :
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(SettingsPreferences.getInstance(context.dataStore)) as T
        }
    }

    companion object {
        fun getInstance(context: Context) = Factory(context)
    }
}