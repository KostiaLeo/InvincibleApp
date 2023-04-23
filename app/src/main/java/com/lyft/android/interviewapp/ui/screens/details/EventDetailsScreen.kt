package com.lyft.android.interviewapp.ui.screens.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun EventDetailsScreen(viewModel: EventDetailsViewModel = hiltViewModel()) {
    val state by viewModel.uiStateFlow.collectAsStateWithLifecycle()

}