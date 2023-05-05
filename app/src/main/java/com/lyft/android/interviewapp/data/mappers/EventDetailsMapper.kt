package com.lyft.android.interviewapp.data.mappers

import com.lyft.android.interviewapp.data.remote.models.EventDetailsResponse
import com.lyft.android.interviewapp.data.remote.models.ShortEvent
import com.lyft.android.interviewapp.data.repository.models.EventDetailsUiModel
import com.lyft.android.interviewapp.data.repository.models.ShortEventUiModel
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*
import java.util.concurrent.TimeUnit

private val dateFormat by lazy(LazyThreadSafetyMode.NONE) {
    SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
}

private val timeFormat by lazy(LazyThreadSafetyMode.NONE) {
    SimpleDateFormat("hh:mm", Locale.getDefault())
}

fun EventDetailsResponse.mapToUiModel(): EventDetailsUiModel {
    val dateTime = eventInfo.date.toDate()
    val gamePoints = "+${eventInfo.gamePoints}G"
    val volunteersCount = "${eventInfo.curVolunteers}/${eventInfo.maxVolunteers}"
    val donationsCount = "${eventInfo.moneyDonated}/${eventInfo.moneyNeeded}"

    return EventDetailsUiModel(
        id = eventInfo.id,
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
    val formattedDate = date.toDate()
    val timeRange = date.toTimeRange()
    val gamePoints = "+10G"
    val volunteersCount = "$curVolunteers/$maxVolunteers"

    return ShortEventUiModel(
        id = id,
        date = formattedDate,
        timeRange = timeRange,
        gamePoints = gamePoints,
        name = name,
        location = location,
        volunteersCount = volunteersCount
    )
}

private fun String.toDate(): String {
    val millis = Instant.parse(this).toEpochMilli()
    return dateFormat.format(millis)
}

private fun String.toTimeRange(): String {
    val startMillis = Instant.parse(this).toEpochMilli()
    val endMillis = startMillis + TimeUnit.HOURS.toMillis(2)

    return "${timeFormat.format(startMillis)} - ${timeFormat.format(endMillis)}"
}
