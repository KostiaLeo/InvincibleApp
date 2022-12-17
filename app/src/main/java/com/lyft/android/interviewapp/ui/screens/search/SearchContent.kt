package com.lyft.android.interviewapp.ui.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lyft.android.interviewapp.data.repository.models.ShortEventUiModel
import com.lyft.android.interviewapp.ui.theme.EventMetadataColor
import com.lyft.android.interviewapp.ui.theme.OnSurfacePrimary
import com.lyft.android.interviewapp.ui.theme.OnSurfaceSecondary
import com.lyft.android.interviewapp.ui.theme.SearchScreenBackground

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun SearchContent(
    viewModel: SearchViewModel,
    onNavigateToEventDetails: (id: String) -> Unit
) {
    val state by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SearchScreenBackground)
    ) {
        Column(
            modifier = Modifier
                .height(0.dp)
                .weight(1f)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(state.events, key = { it.id }) { event ->
                    EventCard(
                        event = event,
                        onEventClicked = {
                            onNavigateToEventDetails(event.id)
                        }
                    )
                }
            }
        }
        BottomNavigation(
            modifier = Modifier
                .height(72.dp),
            backgroundColor = Color.White
        ) {

        }
    }
}

@Composable
fun EventCard(event: ShortEventUiModel, onEventClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .width(256.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onEventClicked)
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(event.iconResourceId),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Row(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp)
            ) {
                EventMetadata(metadata = event.dateTime)

                Spacer(modifier = Modifier.width(8.dp))

                EventMetadata(metadata = event.gamePoints)
            }
            Text(
                text = event.name,
                fontSize = 18.sp,
                color = OnSurfacePrimary,
                fontWeight = FontWeight.W500,
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp)
            )
            Text(
                text = event.location,
                fontSize = 16.sp,
                color = OnSurfaceSecondary,
                fontWeight = FontWeight.W300,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = event.donationsCount,
                    fontSize = 18.sp,
                    color = OnSurfacePrimary,
                    fontWeight = FontWeight.W400
                )
                Text(
                    text = event.volunteersCount,
                    fontSize = 18.sp,
                    color = OnSurfacePrimary,
                    fontWeight = FontWeight.W400
                )
            }
        }
    }
}

@Composable
fun EventMetadata(metadata: String) {
    val metadataShape = RoundedCornerShape(4.dp)
    Text(
        text = metadata,
        color = EventMetadataColor,
        modifier = Modifier
            .clip(metadataShape)
            .border(1.dp, EventMetadataColor, metadataShape)
            .padding(vertical = 4.dp, horizontal = 8.dp)
    )
}