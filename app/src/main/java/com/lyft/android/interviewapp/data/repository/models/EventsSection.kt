package com.lyft.android.interviewapp.data.repository.models

import androidx.annotation.StringRes
import com.lyft.android.interviewapp.data.remote.models.Event

data class EventsSection(
    @StringRes val sectionNameResourceId: Int,
    val events: List<Event>
)
