package com.lyft.android.interviewapp.data.repository

import com.lyft.android.interviewapp.data.mappers.mapToUiModel
import com.lyft.android.interviewapp.data.remote.api.VolunteerEventsApi
import com.lyft.android.interviewapp.data.remote.models.RegisterForEventResponse
import com.lyft.android.interviewapp.data.repository.models.EventDetailsUiModel
import com.lyft.android.interviewapp.data.repository.models.ShortEventUiModel
import com.lyft.android.interviewapp.ui.screens.onboarding.City
import com.lyft.android.interviewapp.utils.DispatcherProvider
import com.lyft.android.interviewapp.utils.EventFilter
import com.lyft.android.interviewapp.utils.toRequestBody
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteVolunteerEventsRepository @Inject constructor(
    private val volunteerEventsApi: VolunteerEventsApi,
    private val dispatcherProvider: DispatcherProvider
) : VolunteerEventsRepository {

    override suspend fun getAllEvents(
        filter: EventFilter?,
        selectedCity: City?
    ): List<ShortEventUiModel> =
        withContext(dispatcherProvider.io) {
            volunteerEventsApi.getAllEvents(
                filter?.id,
                selectedCity?.code ?: -1
            ).events.map { it.mapToUiModel() }
        }

    override suspend fun registerForEvent(eventId: String): RegisterForEventResponse =
        volunteerEventsApi.registerForEvent(eventId)

    override suspend fun getEventDetails(id: String): EventDetailsUiModel =
        withContext(dispatcherProvider.io) {
            volunteerEventsApi.getEventDetails(id).mapToUiModel()
        }

    override suspend fun confirmPresence(qrCodeContent: String) {
        volunteerEventsApi.confirmPresence(ConfirmPresenceRequest(qrCodeContent).toRequestBody())
    }

    override suspend fun getMyMissionsList(filter: EventFilter?): List<ShortEventUiModel> =
        withContext(dispatcherProvider.io) {
            volunteerEventsApi.getMyMissionsList().events.map { it.mapToUiModel() }
        }
}

private data class ConfirmPresenceRequest(val qrCodeContent: String)