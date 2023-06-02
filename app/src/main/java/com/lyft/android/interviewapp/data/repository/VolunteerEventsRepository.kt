package com.lyft.android.interviewapp.data.repository

import com.lyft.android.interviewapp.data.repository.models.EventDetailsUiModel
import com.lyft.android.interviewapp.data.repository.models.ShortEventUiModel
import com.lyft.android.interviewapp.ui.screens.onboarding.City
import com.lyft.android.interviewapp.utils.EventFilter

interface VolunteerEventsRepository {
    suspend fun getAllEvents(
        filter: EventFilter? = null,
        selectedCity: City? = null
    ): List<ShortEventUiModel>

    suspend fun registerForEvent(eventId: String)
    suspend fun confirmPresence(qrCodeContent: String)
    suspend fun getEventDetails(id: String): EventDetailsUiModel
    suspend fun getMyMissionsList(filter: EventFilter? = null): List<ShortEventUiModel>
}