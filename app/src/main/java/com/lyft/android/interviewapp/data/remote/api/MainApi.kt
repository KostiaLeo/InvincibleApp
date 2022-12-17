package com.lyft.android.interviewapp.data.remote.api

import com.lyft.android.interviewapp.data.remote.models.AllUsersResponse
import com.lyft.android.interviewapp.data.remote.models.User
import com.lyft.android.interviewapp.data.remote.models.RuslanRequestBody
import com.lyft.android.interviewapp.data.remote.models.UserRequestBody
import com.lyft.android.interviewapp.data.remote.models.WeatherApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface MainApi {

    @GET("/WeatherForecast")
    suspend fun getWeatherForecast(): WeatherApiResponse

    @POST("/api/user/ruslan")
    suspend fun getRuslan(
        @Query("is_bitch") isBitch: Boolean
    ): User

    @POST("/api/user/ruslan")
    suspend fun getRuslanBody(
        @Body ruslanRequestBody: RuslanRequestBody
    ): User

    @POST("api/user/create")
    suspend fun createUser(
        @Body userRequestBody: UserRequestBody
    )

    @GET("api/user/all")
    suspend fun getAllUsers(): AllUsersResponse
}