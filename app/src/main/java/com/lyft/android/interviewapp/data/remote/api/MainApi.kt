package com.lyft.android.interviewapp.data.remote.api

import com.lyft.android.interviewapp.data.remote.models.MainApiModel
import retrofit2.http.GET

interface MainApi {

    @GET("path")
    suspend fun getMainModel(): MainApiModel
}