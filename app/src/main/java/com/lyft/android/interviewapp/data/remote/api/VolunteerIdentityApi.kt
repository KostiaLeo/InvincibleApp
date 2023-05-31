package com.lyft.android.interviewapp.data.remote.api

import com.lyft.android.interviewapp.data.remote.models.LoginResponse
import com.lyft.android.interviewapp.data.remote.models.MyInfoResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface VolunteerIdentityApi {

    @POST("login")
    suspend fun login(@Body requestBody: RequestBody): LoginResponse

    @PUT("volunteer")
    suspend fun createAccount(@Body requestBody: RequestBody)

    @GET("volunteer/my/info")
    suspend fun getMyInfo(): MyInfoResponse
}