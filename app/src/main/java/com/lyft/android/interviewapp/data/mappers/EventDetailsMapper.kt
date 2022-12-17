package com.lyft.android.interviewapp.data.mappers

import com.lyft.android.interviewapp.data.remote.models.EventDetailsResponse
import com.lyft.android.interviewapp.data.repository.models.EventDetailsUiModel
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Locale

private val dateFormat by lazy(LazyThreadSafetyMode.NONE) {
    SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.ENGLISH)
}

fun EventDetailsResponse.mapToUiModel(): EventDetailsUiModel {
    val millis = Instant.parse(eventInfo.date).toEpochMilli()
    val dateTime = dateFormat.format(millis)

    val gamePoints = "+${eventInfo.gamePoints}G"
    val volunteersCount = "${eventInfo.curVolunteers}/${eventInfo.maxVolunteers}"
    val donationsCount = "${eventInfo.moneyDonated}/${eventInfo.moneyNeeded}"

    return EventDetailsUiModel(
        dateTime = dateTime,
        gamePoints = gamePoints,
        name = eventInfo.name,
        location = eventInfo.location,
        volunteersCount = volunteersCount,
        donationsCount = donationsCount,
        organizer = eventInfo.organizer,
        description = eventInfo.desctiption,
        duties = eventInfo.duties,
        isRegistered = eventInfo.isRegistered
    )
}