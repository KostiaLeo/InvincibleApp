package com.lyft.android.interviewapp.data.mappers

import com.lyft.android.interviewapp.R
import com.lyft.android.interviewapp.data.remote.models.EventDetailsResponse
import com.lyft.android.interviewapp.data.remote.models.ShortEvent
import com.lyft.android.interviewapp.data.repository.models.EventDetailsUiModel
import com.lyft.android.interviewapp.data.repository.models.ShortEventUiModel
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

private val dateFormat by lazy(LazyThreadSafetyMode.NONE) {
    SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.ENGLISH)
}

fun EventDetailsResponse.mapToUiModel(): EventDetailsUiModel {
    val dateTime = eventInfo.date.toDateTime()
    val gamePoints = "+${eventInfo.gamePoints}G"
    val volunteersCount = "${eventInfo.curVolunteers}/${eventInfo.maxVolunteers}"
    val donationsCount = "${eventInfo.moneyDonated}/${eventInfo.moneyNeeded}"

    return EventDetailsUiModel(
        id = eventInfo.id,
        iconResourceId = eventInfo.type.getIconFromType(),
        dateTime = dateTime,
        gamePoints = gamePoints,
        name = eventInfo.name,
        location = eventInfo.location,
        volunteersCount = volunteersCount,
        donationsCount = donationsCount,
        organizer = eventInfo.organizer,
        description = eventInfo.desctiption,
        duties = eventInfo.duties.orEmpty(),
        isRegistered = eventInfo.isRegistered
    )
}

fun ShortEvent.mapToUiModel(): ShortEventUiModel {
    val dateTime = date.toDateTime()
    val gamePoints = "+10G"
    val volunteersCount = "$curVolunteers/$maxVolunteers"

    return ShortEventUiModel(
        id = id,
        iconResourceId = type.getIconFromType(),
        dateTime = dateTime,
        gamePoints = gamePoints,
        name = name,
        location = location,
        volunteersCount = volunteersCount
    )
}

private fun String.toDateTime(): String {
    val millis = Instant.parse(this).toEpochMilli()
    return dateFormat.format(millis)
}

private fun Int.getIconFromType(): Int {
    return if (this % 2 == 0) {
        R.drawable.event_icon_1
    } else {
        R.drawable.event_icon_2
    }
}