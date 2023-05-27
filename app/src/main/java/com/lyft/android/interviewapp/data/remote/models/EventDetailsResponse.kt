package com.lyft.android.interviewapp.data.remote.models

data class EventDetailsResponse(
    val eventInfo: EventInfo
)

data class EventInfo(
    val desctiption: String,
    val duties: String?,
    val id: String,
    val name: String,
    val location: String,
    val date: String,
    val type: Int,
    val curVolunteers: Int,
    val maxVolunteers: Int,
    val organizer: String?,
)
