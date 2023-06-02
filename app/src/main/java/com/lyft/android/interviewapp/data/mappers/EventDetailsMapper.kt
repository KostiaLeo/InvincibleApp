package com.lyft.android.interviewapp.data.mappers

import com.lyft.android.interviewapp.data.remote.models.EventDetailsResponse
import com.lyft.android.interviewapp.data.remote.models.ShortEvent
import com.lyft.android.interviewapp.data.repository.models.EventDetailsUiModel
import com.lyft.android.interviewapp.data.repository.models.EventStatus
import com.lyft.android.interviewapp.data.repository.models.RegisterButtonConfigs
import com.lyft.android.interviewapp.data.repository.models.RegistrationStatus
import com.lyft.android.interviewapp.data.repository.models.ShortEventUiModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Locale
import java.util.concurrent.TimeUnit

private val dateFormat by lazy(LazyThreadSafetyMode.NONE) {
    val ukrainian = Locale("uk", "UA")
    DateFormat.getDateInstance(DateFormat.LONG, ukrainian)
}

private val timeFormat by lazy(LazyThreadSafetyMode.NONE) {
    SimpleDateFormat("HH:mm", Locale.getDefault())
}

fun EventDetailsResponse.mapToUiModel(): EventDetailsUiModel {
    val dateTime = eventInfo.date.toDateTime(eventInfo.length)
    val volunteersCount = "${eventInfo.curVolunteers}/${eventInfo.maxVolunteers}"
    val eventStatus = EventStatus.fromInt(eventInfo.status)
    val registrationStatus = RegistrationStatus.fromInt(eventInfo.volunteerStatus)

    return EventDetailsUiModel(
        id = eventInfo.id,
        dateTime = dateTime,
        name = eventInfo.name,
        location = eventInfo.location,
        volunteersCount = volunteersCount,
        organizer = eventInfo.organizer,
        description = eventInfo.description,
        photos = emptyList(),
        buttonConfigs = RegisterButtonConfigs.fromEventStatus(eventStatus, registrationStatus)
    )
}

fun ShortEvent.mapToUiModel(): ShortEventUiModel {
    val volunteersCount = "$curVolunteers/$maxVolunteers"

    return ShortEventUiModel(
        id = id,
        dateTime = date.toDateTime(length),
        name = name,
        location = location,
        volunteersCount = volunteersCount,
        eventStatus = EventStatus.fromInt(status)
    )
}

private fun String.toDateTime(duration: Int): String {
    val millis = Instant.parse(this).toEpochMilli()
    val date = dateFormat.format(millis)

    val startMillis = Instant.parse(this).toEpochMilli()
    val endMillis = startMillis + TimeUnit.HOURS.toMillis(duration.toLong())
    val timeRange = "${timeFormat.format(startMillis)} - ${timeFormat.format(endMillis)}"

    return "$date • $timeRange"
}
