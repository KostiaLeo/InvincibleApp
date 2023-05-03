package com.lyft.android.interviewapp.ui.tools

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.lyft.android.interviewapp.ui.theme.PrimaryColor

@Composable
fun LoadingOverlay(
    show: Boolean,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(visible = !show, enter = fadeIn(), exit = fadeOut()) {
            content()
        }
        AnimatedVisibility(visible = show, enter = fadeIn(), exit = fadeOut()) {
            CircularProgressIndicator(color = PrimaryColor)
        }
    }
}