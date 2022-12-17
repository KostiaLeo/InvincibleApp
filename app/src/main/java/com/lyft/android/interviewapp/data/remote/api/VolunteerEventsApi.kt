package com.lyft.android.interviewapp.data.remote.api

import com.lyft.android.interviewapp.data.remote.models.AllEventsApiResponse
import com.lyft.android.interviewapp.data.remote.models.AllUsersResponse
import com.lyft.android.interviewapp.data.remote.models.EventDetailsResponse
import com.lyft.android.interviewapp.data.remote.models.RegisterForEventResponse
import com.lyft.android.interviewapp.data.remote.models.User
import com.lyft.android.interviewapp.data.remote.models.RuslanRequestBody
import com.lyft.android.interviewapp.data.remote.models.UserRequestBody
import com.lyft.android.interviewapp.data.remote.models.WeatherApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface VolunteerEventsApi {

    @GET("api/events")
    suspend fun getAllEvents(): AllEventsApiResponse

    @GET("api/events/{id}")
    suspend fun getEventDetails(@Path("id") id: String): EventDetailsResponse

    @GET("api/events/{id}/register")
    suspend fun registerForEvent(@Path("id") id: String): RegisterForEventResponse
}