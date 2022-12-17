package com.lyft.android.interviewapp.fakesource

import com.lyft.android.interviewapp.data.remote.api.VolunteerEventsApi
import com.lyft.android.interviewapp.data.remote.models.WeatherApiResponse

class FakeApi : VolunteerEventsApi {
    override suspend fun getWeatherForecast(): WeatherApiResponse {
        return WeatherApiResponse(0)
    }
}