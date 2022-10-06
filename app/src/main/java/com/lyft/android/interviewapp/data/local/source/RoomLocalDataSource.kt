package com.lyft.android.interviewapp.data.local.source

import com.lyft.android.interviewapp.data.local.dao.MainDao
import javax.inject.Inject

class RoomLocalDataSource @Inject constructor(
    private val mainDao: MainDao
): LocalDataSource {

}