package com.lyft.android.interviewapp.data.repository

import com.lyft.android.interviewapp.data.remote.models.User
import com.lyft.android.interviewapp.data.remote.models.WeatherApiResponse

interface Repository {
    suspend fun getWeather(): WeatherApiResponse
    suspend fun getRuslan(isB: Boolean): User
    suspend fun getRuslanBody(isB: Boolean): User

    suspend fun createUser(name: String, age: Int, isB: Boolean)
    suspend fun getAllUsers(): List<User>
}