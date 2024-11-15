package com.khrlanamm.eventlist.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khrlanamm.eventlist.data.response.EventDetail
import com.khrlanamm.eventlist.data.retrofit.ApiConfig
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _upcomingEvents = MutableLiveData<List<EventDetail>>()
    val upcomingEvents: LiveData<List<EventDetail>> get() = _upcomingEvents

    private val _finishedEvents = MutableLiveData<List<EventDetail>>()
    val finishedEvents: LiveData<List<EventDetail>> get() = _finishedEvents

    private val _searchedEvents = MutableLiveData<List<EventDetail>>()
    val searchedEvents: LiveData<List<EventDetail>> get() = _searchedEvents

    private val _isUpcomingLoading = MutableLiveData<Boolean>()
    val isUpcomingLoading: LiveData<Boolean> get() = _isUpcomingLoading

    private val _isFinishedLoading = MutableLiveData<Boolean>()
    val isFinishedLoading: LiveData<Boolean> get() = _isFinishedLoading

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    init {
        fetchUpcomingEvents()  // Fetch upcoming events on ViewModel initialization
        fetchFinishedEvents()  // Fetch finished events on ViewModel initialization
    }

    private fun fetchUpcomingEvents() {
        viewModelScope.launch {
            _isUpcomingLoading.value = true
            try {
                val response = ApiConfig.api.getEvents(active = 1)
                if (!response.error) {
                    _upcomingEvents.postValue(response.listEvents)
                } else {
                    _errorMessage.postValue("Error fetching events: ${response.message}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to load events: ${e.message}")
            } finally {
                _isUpcomingLoading.value = false
                updateLoadingState()
            }
        }
    }

    private fun fetchFinishedEvents() {
        viewModelScope.launch {
            _isFinishedLoading.value = true
            try {
                val response = ApiConfig.api.getEvents(active = 0)
                if (!response.error) {
                    _finishedEvents.postValue(response.listEvents)
                } else {
                    _errorMessage.postValue("Error fetching events: ${response.message}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to load events: ${e.message}")
            } finally {
                _isFinishedLoading.value = false
                updateLoadingState()
            }
        }
    }

    private fun updateLoadingState() {
        _isLoading.value = _isUpcomingLoading.value == true || _isFinishedLoading.value == true
    }


    // Function to search for events using a query string
    fun searchEvents(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = ApiConfig.api.getEvents(active = -1, query = query)
                if (!response.error) {
                    _searchedEvents.postValue(response.listEvents)  // Update the live data with search results
                } else {
                    _errorMessage.postValue("Error fetching events: ${response.message}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to load events: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
