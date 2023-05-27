package com.lyft.android.interviewapp.data.mappers

import com.lyft.android.interviewapp.data.remote.models.EventDetailsResponse
import com.lyft.android.interviewapp.data.remote.models.ShortEvent
import com.lyft.android.interviewapp.data.repository.models.EventDetailsUiModel
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
    SimpleDateFormat("hh:mm", Locale.getDefault())
}

fun EventDetailsResponse.mapToUiModel(): EventDetailsUiModel {
    val dateTime = eventInfo.date.toDateTime()
    val volunteersCount = "${eventInfo.curVolunteers}/${eventInfo.maxVolunteers}"

    return EventDetailsUiModel(
        id = eventInfo.id,
        dateTime = dateTime,
        name = eventInfo.name,
        location = eventInfo.location,
        volunteersCount = volunteersCount,
        organizer = eventInfo.organizer ?: "Руслан організатор",
        description = eventInfo.desctiption,
        duties = eventInfo.duties.orEmpty(),
        photos = emptyList(),
        registrationStatus = RegistrationStatus.AVAILABLE
    )
}

fun ShortEvent.mapToUiModel(): ShortEventUiModel {
    val volunteersCount = "$curVolunteers/$maxVolunteers"

    return ShortEventUiModel(
        id = id,
        dateTime = date.toDateTime(),
        name = name,
        location = location,
        volunteersCount = volunteersCount
    )
}

private fun String.toDateTime(): String {
    val millis = Instant.parse(this).toEpochMilli()
    val date = dateFormat.format(millis)

    val startMillis = Instant.parse(this).toEpochMilli()
    val endMillis = startMillis + TimeUnit.HOURS.toMillis(2)
    val timeRange = "${timeFormat.format(startMillis)} - ${timeFormat.format(endMillis)}"

    return "$date • $timeRange"
}
