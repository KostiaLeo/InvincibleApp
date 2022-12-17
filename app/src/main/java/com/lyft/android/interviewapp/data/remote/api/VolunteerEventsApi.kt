package com.lyft.android.interviewapp.data.remote.api

import com.lyft.android.interviewapp.data.remote.models.AllEventsApiResponse
import com.lyft.android.interviewapp.data.remote.models.EventDetailsResponse
import com.lyft.android.interviewapp.data.remote.models.RegisterForEventResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface VolunteerEventsApi {

    @GET("api/events")
    suspend fun getAllEvents(): AllEventsApiResponse

    @GET("api/events/{id}")
    suspend fun getEventDetails(@Path("id") id: String): EventDetailsResponse

    @POST("api/events/{id}/register")
    suspend fun registerForEvent(@Path("id") id: String): RegisterForEventResponse
}