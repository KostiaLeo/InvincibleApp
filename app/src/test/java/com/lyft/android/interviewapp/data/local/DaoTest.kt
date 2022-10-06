package com.lyft.android.interviewapp.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lyft.android.interviewapp.data.local.dao.MainDao
import com.lyft.android.interviewapp.data.local.db.MainDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class DaoTest {
    private lateinit var database: MainDatabase
    private lateinit var dao: MainDao

    @Before
    fun initDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, MainDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.mainDao
    }

    @Test
    fun `test dao`() = runTest {

    }

    @After
    fun closeDB() {
        database.close()
    }
}