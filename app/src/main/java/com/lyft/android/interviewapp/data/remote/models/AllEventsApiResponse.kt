package com.lyft.android.interviewapp.data.remote.models

data class AllEventsApiResponse(
    val events: List<Event>
)

data class Event(
    val id: String,
    val name: String,
    val date: String,
    val gamePoints: Int,
    val location: String,
    val curVolunteers: Int,
    val maxVolunteers: Int,
    val moneyDonated: Int,
    val moneyNeeded: Int,
    val type: Int
)