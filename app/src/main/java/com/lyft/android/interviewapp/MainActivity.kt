package com.lyft.android.interviewapp

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.lyft.android.interviewapp.di.isSignedIn
import com.lyft.android.interviewapp.ui.navigation.AppNavHost
import com.lyft.android.interviewapp.ui.navigation.Routes
import com.lyft.android.interviewapp.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val startDestination =
                    if (sharedPreferences.isSignedIn) Routes.home else Routes.login
                AppNavHost(startDestination = startDestination)
            }
        }
    }
}