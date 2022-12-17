package com.lyft.android.interviewapp.data.repository

import com.lyft.android.interviewapp.data.remote.api.MainApi
import com.lyft.android.interviewapp.data.remote.models.User
import com.lyft.android.interviewapp.data.remote.models.RuslanRequestBody
import com.lyft.android.interviewapp.data.remote.models.UserRequestBody
import com.lyft.android.interviewapp.data.remote.models.WeatherApiResponse
import com.lyft.android.interviewapp.utils.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    private val mainApi: MainApi,
    private val dispatcherProvider: DispatcherProvider
) : Repository {

    override suspend fun getWeather(): WeatherApiResponse = withContext(dispatcherProvider.io) {
        mainApi.getWeatherForecast()
    }

    override suspend fun getRuslan(isB: Boolean): User = withContext(dispatcherProvider.io) {
        mainApi.getRuslan(isB)
    }

    override suspend fun getRuslanBody(isB: Boolean): User = withContext(dispatcherProvider.io) {
        val requestBody = RuslanRequestBody(isB)
        mainApi.getRuslanBody(requestBody)
    }

    override suspend fun createUser(name: String, age: Int, isB: Boolean) {
        val requestBody = UserRequestBody(User(name, age, isB))
        mainApi.createUser(requestBody)
    }

    override suspend fun getAllUsers(): List<User> {
        return mainApi.getAllUsers().users
    }
}