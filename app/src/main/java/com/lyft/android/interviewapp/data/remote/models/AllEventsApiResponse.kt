package com.lyft.android.interviewapp.data.remote.models

data class AllEventsApiResponse(
    val events: List<ShortEvent>
)

data class ShortEvent(
    val id: String,
    val name: String,
    val date: String,
    val location: String,
    val curVolunteers: Int,
    val maxVolunteers: Int,
    val type: Int
)