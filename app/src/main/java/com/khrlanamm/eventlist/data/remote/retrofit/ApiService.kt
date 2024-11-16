package com.khrlanamm.eventlist.data.remote.retrofit

import com.khrlanamm.eventlist.data.remote.response.Event
import retrofit2.http.*

interface ApiService {
    @GET("events")
    suspend fun getEvents(
        @Query("active") active: Int = 1,
        @Query("q") query: String? = null,
        @Query("limit") limit: Int = 40
    ): Event
}
