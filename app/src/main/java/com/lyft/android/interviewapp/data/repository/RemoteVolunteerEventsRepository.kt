package com.lyft.android.interviewapp.data.repository

import android.content.res.Resources
import com.lyft.android.interviewapp.data.mappers.mapToUiModel
import com.lyft.android.interviewapp.data.remote.api.VolunteerEventsApi
import com.lyft.android.interviewapp.data.remote.models.RegisterForEventResponse
import com.lyft.android.interviewapp.data.repository.models.EventDetailsUiModel
import com.lyft.android.interviewapp.data.repository.models.EventsSection
import com.lyft.android.interviewapp.utils.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteVolunteerEventsRepository @Inject constructor(
    private val volunteerEventsApi: VolunteerEventsApi,
    private val dispatcherProvider: DispatcherProvider,
    private val resources: Resources
) : VolunteerEventsRepository {

    override suspend fun getAllEvents(): List<EventsSection> = withContext(dispatcherProvider.io) {
        volunteerEventsApi.getAllEvents()
            .events
            .groupBy { it.type }
            .map { (typeId, events) ->
                EventsSection(mapTypeToNameResId(typeId), events)
            }
    }

    private fun mapTypeToNameResId(typeId: Int): Int {
        return typeId
//        return when (typeId) {
//            0 ->
//        }
    }

    override suspend fun registerForEvent(eventId: String): RegisterForEventResponse {
        return volunteerEventsApi.registerForEvent(eventId)
    }

    override suspend fun getEventDetails(id: String): EventDetailsUiModel {
        return volunteerEventsApi.getEventDetails(id).mapToUiModel()
    }
}