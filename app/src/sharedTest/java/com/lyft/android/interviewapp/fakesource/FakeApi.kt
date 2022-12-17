package com.lyft.android.interviewapp.fakesource

import com.lyft.android.interviewapp.data.remote.api.MainApi
import com.lyft.android.interviewapp.data.remote.models.WeatherApiResponse

class FakeApi : MainApi {
    override suspend fun getWeatherForecast(): WeatherApiResponse {
        return WeatherApiResponse(0)
    }
}