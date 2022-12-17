package com.lyft.android.interviewapp.data.remote.models

data class WeatherApiResponse(
    val forecasts: List<ForecastApiModel>
)

data class ForecastApiModel(
    val date: String,
    val temperatureC: String,
    val temperatureF: String,
    val summary: String
)

data class User(
    val name: String,
    val age: Int,
    val isBitch: Boolean
)

data class RuslanRequestBody(
    val isBitch: Boolean
)

data class AllUsersResponse(
    val users: List<User> = emptyList()
)

data class UserRequestBody(
    val user: User
)