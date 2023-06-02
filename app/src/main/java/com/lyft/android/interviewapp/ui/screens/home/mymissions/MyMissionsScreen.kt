package com.lyft.android.interviewapp.ui.screens.home.mymissions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lyft.android.interviewapp.ui.screens.home.search.content.EventCard
import com.lyft.android.interviewapp.ui.screens.home.search.content.EventsFiltersTopBar
import com.lyft.android.interviewapp.ui.screens.home.search.content.previewEvent
import com.lyft.android.interviewapp.ui.theme.AppTheme
import com.lyft.android.interviewapp.ui.theme.HintTextColor
import com.lyft.android.interviewapp.ui.theme.LightGrayBackgroundColor
import com.lyft.android.interviewapp.utils.EventFilter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyMissionsScreen(
    state: MyMissionsUiState,
    onEventClicked: (id: String) -> Unit,
    onFilterSelected: (filter: EventFilter) -> Unit,
    onQrCodeClicked: () -> Unit,
    onRefresh: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        EventsFiltersTopBar(
            state.availableFilters,
            title = {
                Text(
                    text = "Мої місії",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500
                )
            },
            onQrCodeClicked,
            onFilterSelected
        )

        val pullRefreshState = rememberPullRefreshState(state.isLoading, onRefresh = onRefresh)

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(LightGrayBackgroundColor)
                .pullRefresh(state = pullRefreshState),
            contentAlignment = Alignment.Center
        ) {
            if (state.events.isEmpty()) {
                Text(
                    text = "Місій не знайдено",
                    color = HintTextColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W300
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.events, key = { it.id }) { event ->
                        EventCard(
                            event = event,
                            onEventClicked = {
                                onEventClicked(event.id)
                            },
                            showStatus = true
                        )
                    }
                }
            }
            PullRefreshIndicator(
                refreshing = state.isLoading,
                state = pullRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Preview
@Composable
fun MyMissionsScreenPreview() {
    AppTheme {
        MyMissionsScreen(
            state = MyMissionsUiState(
                isLoading = true,
                events = listOf(previewEvent, previewEvent.copy(id = "2")),
            ),
            onEventClicked = {},
            onFilterSelected = {},
            onQrCodeClicked = {},
            onRefresh = {}
        )
    }
}