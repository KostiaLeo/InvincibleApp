package com.lyft.android.interviewapp.data.remote.api

import com.lyft.android.interviewapp.data.remote.models.EventDetailsResponse
import com.lyft.android.interviewapp.data.remote.models.EventsListApiResponse
import com.lyft.android.interviewapp.data.remote.models.RegisterForEventResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface VolunteerEventsApi {

    @GET("events")
    suspend fun getAllEvents(): EventsListApiResponse

    @GET("events/{id}")
    suspend fun getEventDetails(@Path("id") id: String): EventDetailsResponse

    @GET("events/{id}/register")
    suspend fun registerForEvent(@Path("id") id: String): RegisterForEventResponse

    @POST("events/confirm")
    suspend fun confirmPresence(@Body requestBody: RequestBody)

    @GET("events/my")
    suspend fun getMyMissionsList(): EventsListApiResponse
}