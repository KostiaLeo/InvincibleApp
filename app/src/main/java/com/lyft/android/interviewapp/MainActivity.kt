package com.lyft.android.interviewapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.lyft.android.interviewapp.ui.navigation.AppNavHost
import com.lyft.android.interviewapp.ui.theme.InterviewAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InterviewAppTheme {
                AppNavHost()
            }
        }
//        setContentView(R.layout.activity_main)
        window.navigationBarColor = getColor(android.R.color.white)
    }
}