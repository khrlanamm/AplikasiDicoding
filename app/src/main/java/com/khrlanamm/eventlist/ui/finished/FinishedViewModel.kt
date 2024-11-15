package com.khrlanamm.eventlist.ui.finished

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khrlanamm.eventlist.data.response.EventDetail
import com.khrlanamm.eventlist.data.retrofit.ApiConfig
import kotlinx.coroutines.launch


class FinishedViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _pastEvents = MutableLiveData<List<EventDetail>>()
    val pastEvents: LiveData<List<EventDetail>> get() = _pastEvents

    init {
        fetchPastEvents()
    }

    private fun fetchPastEvents() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val response = ApiConfig.api.getEvents(active = 0)
                _pastEvents.postValue(response.listEvents)
                _isLoading.postValue(false)
            } catch (e: Exception) {
                _isLoading.postValue(false)
                _pastEvents.postValue(emptyList()) // In case of failure, post empty list
            }
        }
    }
}
