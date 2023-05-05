package com.lyft.android.interviewapp.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalStorageModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences("storage", Context.MODE_PRIVATE)
    }
}

var SharedPreferences.idToken: String?
    get() = getString("idToken", null)
    set(value) = edit { putString("idToken", value) }

var SharedPreferences.isSignedIn: Boolean
    get() = getBoolean("isSignedIn", false)
    set(value) = edit { putBoolean("isSignedIn", value) }