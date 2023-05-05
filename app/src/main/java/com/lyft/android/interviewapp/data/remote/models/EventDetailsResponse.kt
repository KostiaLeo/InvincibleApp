package com.lyft.android.interviewapp.data.remote.models

data class EventDetailsResponse(
    val eventInfo: EventInfo
)

data class EventInfo(
    val curVolunteers: Int,
    val date: String,
    val desctiption: String,
    val duties: String?,
    val gamePoints: Int,
    val id: String,
    val isRegistered: Boolean,
    val location: String,
    val maxVolunteers: Int,
    val moneyDonated: Int,
    val moneyNeeded: Int,
    val name: String,
    val organizer: String,
    val type: Int
)
