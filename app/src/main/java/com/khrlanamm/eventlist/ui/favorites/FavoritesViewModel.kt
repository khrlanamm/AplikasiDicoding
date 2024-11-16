package com.khrlanamm.eventlist.ui.favorites

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khrlanamm.eventlist.data.FavoriteRepository

class FavoritesViewModel private constructor(
    private val favoriteRepository: FavoriteRepository
) :
    ViewModel() {

    fun getAllFavorites() = favoriteRepository.getAllFavorite()

    class Factory(private val context: Context) :
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FavoritesViewModel(FavoriteRepository.getInstance(context)) as T
        }
    }

    companion object {
        fun getInstance(context: Context) = Factory(context)
    }
}