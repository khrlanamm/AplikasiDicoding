package com.khrlanamm.eventlist.data.retrofit

import com.khrlanamm.eventlist.data.response.Event
import retrofit2.http.*

interface ApiService {
    @GET("events")
    suspend fun getEvents(
        @Query("active") active: Int = 1,
        @Query("q") query: String? = null,
        @Query("limit") limit: Int = 40
    ): Event
}
