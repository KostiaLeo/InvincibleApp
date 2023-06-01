package com.lyft.android.interviewapp.data.remote.models

data class EventsListApiResponse(
    val events: List<ShortEvent>
)

data class ShortEvent(
    val id: String,
    val name: String,
    val location: String,
    val cityCode: Int,
    val date: String,
    val length: Int,
    val type: Int,
    val status: Int,
    val curVolunteers: Int,
    val maxVolunteers: Int
)