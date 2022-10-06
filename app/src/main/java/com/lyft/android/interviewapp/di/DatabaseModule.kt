package com.lyft.android.interviewapp.di

import android.content.Context
import androidx.room.Room
import com.lyft.android.interviewapp.data.local.dao.MainDao
import com.lyft.android.interviewapp.data.local.db.MainDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): MainDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MainDatabase::class.java,
            "main.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(database: MainDatabase): MainDao {
        return database.mainDao
    }
}