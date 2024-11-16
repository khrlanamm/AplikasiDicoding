package com.khrlanamm.eventlist.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khrlanamm.eventlist.data.local.entity.EventDetail
import com.khrlanamm.eventlist.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch

class UpcomingViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _upcomingEvents = MutableLiveData<List<EventDetail>>()
    val upcomingEvents: LiveData<List<EventDetail>> get() = _upcomingEvents

    init {
        fetchUpcomingEvents()
    }

    private fun fetchUpcomingEvents() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val response = ApiConfig.api.getEvents(active = 1)
                _upcomingEvents.postValue(response.listEvents)
                _isLoading.postValue(false)
            } catch (e: Exception) {
                _upcomingEvents.postValue(emptyList()) // In case of failure, post empty list
                _isLoading.postValue(false)
            }
        }
    }
}
