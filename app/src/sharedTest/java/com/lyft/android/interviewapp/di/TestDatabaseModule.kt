package com.lyft.android.interviewapp.di

import android.content.Context
import androidx.room.Room
import com.lyft.android.interviewapp.data.local.dao.MainDao
import com.lyft.android.interviewapp.data.local.db.MainDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object TestDatabaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): MainDatabase {
        return Room
            .inMemoryDatabaseBuilder(context.applicationContext, MainDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideMainDao(database: MainDatabase): MainDao {
        return database.mainDao
    }
}