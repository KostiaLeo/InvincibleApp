package com.lyft.android.interviewapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lyft.android.interviewapp.ui.navigation.AppNavHost
import com.lyft.android.interviewapp.ui.theme.InterviewAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InterviewAppTheme {
                AppNavHost()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    InterviewAppTheme {

    }
}