package com.lyft.android.interviewapp.data.repository

import com.lyft.android.interviewapp.data.remote.models.RegisterForEventResponse
import com.lyft.android.interviewapp.data.repository.models.EventDetailsUiModel
import com.lyft.android.interviewapp.data.repository.models.ShortEventUiModel

interface VolunteerEventsRepository {
    suspend fun getAllEvents(): List<ShortEventUiModel>
    suspend fun registerForEvent(eventId: String): RegisterForEventResponse
    suspend fun confirmPresence(qrCodeContent: String)
    suspend fun getEventDetails(id: String): EventDetailsUiModel
    suspend fun getMyMissionsList(): List<ShortEventUiModel>
}