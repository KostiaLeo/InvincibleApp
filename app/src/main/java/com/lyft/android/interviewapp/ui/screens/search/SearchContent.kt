package com.lyft.android.interviewapp.ui.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lyft.android.interviewapp.data.repository.models.ShortEventUiModel
import com.lyft.android.interviewapp.ui.theme.EventMetadataColor
import com.lyft.android.interviewapp.ui.theme.OnSurfacePrimary
import com.lyft.android.interviewapp.ui.theme.OnSurfaceSecondary
import com.lyft.android.interviewapp.ui.theme.SearchScreenBackground
import com.lyft.android.interviewapp.R
import com.lyft.android.interviewapp.ui.theme.eUkraineFontFamily

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
        Box(
            modifier = Modifier
                .height(0.dp)
                .weight(1f)
        ) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(40.dp),
                    shape = RoundedCornerShape(6.dp),
                    elevation = 8.dp
                ) {
                    Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.ic_search_icon),
                            contentDescription = null,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            fontFamily = eUkraineFontFamily,
                            text = "Find your mission",
                            fontSize = 12.sp,
                            letterSpacing = 0.4.sp,
                            color = OnSurfaceSecondary,
                            fontWeight = FontWeight.W300
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    fontFamily = eUkraineFontFamily,
                    text = "Nearest missions",
                    fontWeight = FontWeight.W500,
                    color = OnSurfacePrimary,
                    fontSize = 16.sp,
                    letterSpacing = 0.15.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

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

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    fontFamily = eUkraineFontFamily,
                    text = "Donations",
                    fontWeight = FontWeight.W500,
                    color = OnSurfacePrimary,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                val funds = remember {
                    listOf(
                        "Caritas Ukraine" to R.drawable.donation_caritas,
                        "National Bank" to R.drawable.donation_nbu,
                        "DIM Foundation" to R.drawable.donation_dim_ukraine,
                        "Caritas Ukraine" to R.drawable.donation_caritas,
                        "National Bank" to R.drawable.donation_nbu,
                        "DIM Foundation" to R.drawable.donation_dim_ukraine,
                    )
                }
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(funds) { (name, icon) ->
                        DonationFundCard(
                            fundName = name,
                            iconId = icon
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    fontFamily = eUkraineFontFamily,
                    text = "In your city",
                    fontWeight = FontWeight.W500,
                    color = OnSurfacePrimary,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(state.events.asReversed(), key = { it.id }) { event ->
                        EventCard(
                            event = event,
                            onEventClicked = {
                                onNavigateToEventDetails(event.id)
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        BottomNavigation(
            modifier = Modifier
                .height(72.dp),
            backgroundColor = Color.White,
            elevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_missions),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.height(20.dp)
                )
                Text(
                    fontFamily = eUkraineFontFamily,
                    text = "Missions",
                    color = EventMetadataColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_level),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.height(20.dp)
                )
                Text(
                    fontFamily = eUkraineFontFamily,
                    text = "Your level",
                    color = OnSurfacePrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_user),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.height(20.dp)
                )
                Text(
                    fontFamily = eUkraineFontFamily,
                    text = "Profile",
                    color = OnSurfacePrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300
                )
            }
        }
    }
}

@Composable
fun EventCard(event: ShortEventUiModel, onEventClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .width(256.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = onEventClicked,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple()
                )
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
                fontFamily = eUkraineFontFamily,
                text = event.name,
                fontSize = 14.sp,
                letterSpacing = 0.1.sp,
                color = OnSurfacePrimary,
                fontWeight = FontWeight.W500,
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                fontFamily = eUkraineFontFamily,
                text = event.location,
                fontSize = 12.sp,
                letterSpacing = 0.4.sp,
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_uah_symbol),
                        modifier = Modifier
                            .height(12.dp),
                        contentScale = ContentScale.FillHeight,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        fontFamily = eUkraineFontFamily,
                        text = event.donationsCount,
                        fontSize = 14.sp,
                        color = OnSurfacePrimary,
                        fontWeight = FontWeight.W400
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_user),
                        modifier = Modifier.size(16.dp),
                        contentScale = ContentScale.FillHeight,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        fontFamily = eUkraineFontFamily,
                        text = event.volunteersCount,
                        fontSize = 14.sp,
                        color = OnSurfacePrimary,
                        fontWeight = FontWeight.W400
                    )
                }
            }
        }
    }
}

@Composable
fun EventMetadata(metadata: String) {
    val metadataShape = RoundedCornerShape(4.dp)
    Text(
        fontFamily = eUkraineFontFamily,
        text = metadata,
        fontWeight = FontWeight.W300,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp,
        color = EventMetadataColor,
        modifier = Modifier
            .clip(metadataShape)
            .border(1.dp, EventMetadataColor, metadataShape)
            .padding(vertical = 4.dp, horizontal = 8.dp)
    )
}

@Composable
fun DonationFundCard(iconId: Int, fundName: String) {
    Card(
        modifier = Modifier
            .width(104.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(iconId),
                contentDescription = null,
                modifier = Modifier.height(48.dp),
                contentScale = ContentScale.FillHeight
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                fontFamily = eUkraineFontFamily,
                text = fundName,
                fontSize = 12.sp,
                lineHeight = 15.6.sp,
                letterSpacing = 0.4.sp,
                color = OnSurfacePrimary,
                fontWeight = FontWeight.W500,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}