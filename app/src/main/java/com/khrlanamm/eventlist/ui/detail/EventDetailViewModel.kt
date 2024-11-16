package com.khrlanamm.eventlist.ui.detail

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.khrlanamm.eventlist.data.FavoriteRepository
import com.khrlanamm.eventlist.data.local.entity.EventDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventDetailViewModel private constructor(
    private val favoriteRepository: FavoriteRepository,
    private val id: Int
) :
    ViewModel() {

    val favFromDatabase = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            checkFav(id)
        }
    }

    private suspend fun checkFav(eventId: Int) {
        favoriteRepository.checkFav(eventId).asFlow().collect {
            favFromDatabase.postValue(it)
        }
    }

    fun insert(event: EventDetail) {
        viewModelScope.launch {
            favoriteRepository.insert(event)
        }
    }

    fun deleteEvent() {
        viewModelScope.launch {
            favoriteRepository.delete(id)
        }
    }

    class Factory(private val context: Context, private val id: Int) :
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EventDetailViewModel(FavoriteRepository.getInstance(context), id) as T
        }
    }

    companion object {
        fun getInstance(context: Context, id: Int) = Factory(context, id)
    }
}