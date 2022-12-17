package com.lyft.android.interviewapp.data.repository

import com.lyft.android.interviewapp.data.mappers.mapToUiModel
import com.lyft.android.interviewapp.data.remote.api.VolunteerEventsApi
import com.lyft.android.interviewapp.data.remote.models.RegisterForEventResponse
import com.lyft.android.interviewapp.data.repository.models.EventDetailsUiModel
import com.lyft.android.interviewapp.data.repository.models.ShortEventUiModel
import com.lyft.android.interviewapp.utils.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteVolunteerEventsRepository @Inject constructor(
    private val volunteerEventsApi: VolunteerEventsApi,
    private val dispatcherProvider: DispatcherProvider
) : VolunteerEventsRepository {

    override suspend fun getAllEvents(): List<ShortEventUiModel> = withContext(dispatcherProvider.io) {
        volunteerEventsApi.getAllEvents().events.map { it.mapToUiModel() }
    }

    override suspend fun registerForEvent(eventId: String): RegisterForEventResponse = withContext(dispatcherProvider.io) {
        volunteerEventsApi.registerForEvent(eventId)
    }

    override suspend fun getEventDetails(id: String): EventDetailsUiModel = withContext(dispatcherProvider.io) {
        volunteerEventsApi.getEventDetails(id).mapToUiModel()
    }
}