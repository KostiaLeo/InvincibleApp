package com.lyft.android.interviewapp.data.repository

import com.lyft.android.interviewapp.data.remote.models.RegisterForEventResponse
import com.lyft.android.interviewapp.data.repository.models.EventDetailsUiModel
import com.lyft.android.interviewapp.data.repository.models.EventsSection

interface VolunteerEventsRepository {
    suspend fun getAllEvents(): List<EventsSection>
    suspend fun registerForEvent(eventId: String): RegisterForEventResponse
    suspend fun getEventDetails(id: String): EventDetailsUiModel
}