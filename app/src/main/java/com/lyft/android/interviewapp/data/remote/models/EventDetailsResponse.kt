package com.lyft.android.interviewapp.data.remote.models

import com.squareup.moshi.Json

data class EventDetailsResponse(
    val eventInfo: EventInfo
)

data class EventInfo(
    val id: String,
    val name: String,
    val location: String,
    val cityCode: Int,
    val date: String,
    val length: Int,
    val type: Int,
    val status: Int,
    val curVolunteers: Int,
    val maxVolunteers: Int,
    @Json(name = "desctiption") val description: String,
    val volunteerStatus: Int,
    @Json(name = "organizerInfo") val organizer: OrganizerInfo
)

data class OrganizerInfo(
    val name: String? = null,
    val phoneNumber: String? = null,
    val description: String? = null,
    val statistics: Statistics = Statistics()
)