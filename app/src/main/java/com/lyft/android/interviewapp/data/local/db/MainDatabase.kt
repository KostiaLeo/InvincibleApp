package com.lyft.android.interviewapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lyft.android.interviewapp.data.local.dao.MainDao
import com.lyft.android.interviewapp.data.local.entities.MainEntity

@Database(entities = [MainEntity::class], version = 1)
abstract class MainDatabase : RoomDatabase() {
    abstract val mainDao: MainDao
}