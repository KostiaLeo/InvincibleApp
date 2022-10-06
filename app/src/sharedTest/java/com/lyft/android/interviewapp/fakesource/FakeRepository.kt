package com.lyft.android.interviewapp.fakesource

import com.lyft.android.interviewapp.data.repository.Repository
import java.util.concurrent.atomic.AtomicInteger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class FakeRepository @Inject constructor() : Repository {
    private val _dataFlow = MutableStateFlow(LinkedHashMap<Int, Int>())
}