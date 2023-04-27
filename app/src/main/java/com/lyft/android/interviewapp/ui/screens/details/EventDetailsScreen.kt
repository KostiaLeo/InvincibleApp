package com.lyft.android.interviewapp.ui.screens.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lyft.android.interviewapp.data.repository.models.EventDetailsUiModel
import com.lyft.android.interviewapp.ui.theme.InterviewAppTheme
import com.lyft.android.interviewapp.ui.theme.PrimaryColor

@Composable
fun EventDetailsScreen(
    state: PlaceDetailsUiState,
    onRegisterClicked: () -> Unit,
    onGoBackClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            EventDetailsTopBar(state, onGoBackClicked)
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                Text(text = state.details.name, fontWeight = FontWeight.Bold)
            }
        },
        bottomBar = {
            EventDetailsBottomBar(onRegisterClicked)
        }
    )
}

@Composable
fun EventDetailsTopBar(
    state: PlaceDetailsUiState,
    onGoBackClicked: () -> Unit
) {
    TopAppBar(
        backgroundColor = PrimaryColor,
        contentColor = Color.White,
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier.clickable(onClick = onGoBackClicked)
            )
            Text(text = state.details.organizer)
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
        }
    }
}

@Composable
fun EventDetailsBottomBar(
    onRegisterClicked: () -> Unit
) {
    BottomAppBar(
        backgroundColor = Color.White
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(horizontal = 12.dp)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = PrimaryColor,
                    contentColor = Color.White
                ),
                onClick = onRegisterClicked
            ) {
                Text(text = "Register")
            }
            Button(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(6.dp),
                border = BorderStroke(1.dp, PrimaryColor),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = PrimaryColor
                ),
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Make a donation")
            }
        }
    }
}

@Composable
@Preview
fun EventDetailsScreenPreview() {
    InterviewAppTheme {
        EventDetailsScreen(
            state = PlaceDetailsUiState(
                details = EventDetailsUiModel(
                    name = "Event name",
                    organizer = "Kostia"
                ),
                isLoading = false
            ),
            onRegisterClicked = {},
            onGoBackClicked = {}
        )
    }
}