package com.lyft.android.interviewapp.fakesource

import com.lyft.android.interviewapp.data.repository.VolunteerEventsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class FakeVolunteerEventsRepository @Inject constructor() : VolunteerEventsRepository {
    private val _dataFlow = MutableStateFlow(LinkedHashMap<Int, Int>())
}