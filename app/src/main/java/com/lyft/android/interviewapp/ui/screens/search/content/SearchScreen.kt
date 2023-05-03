package com.lyft.android.interviewapp.ui.screens.search.content

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lyft.android.interviewapp.R
import com.lyft.android.interviewapp.data.repository.models.ShortEventUiModel
import com.lyft.android.interviewapp.ui.screens.search.SearchUiState
import com.lyft.android.interviewapp.ui.screens.search.SearchViewModel
import com.lyft.android.interviewapp.ui.theme.*

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onEventClicked: (id: String) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        (context as AppCompatActivity).run {
            window.navigationBarColor = getColor(android.R.color.white)
            window.statusBarColor = getColor(android.R.color.white)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

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
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(color = PrimaryColor)
            }
            AnimatedVisibility(visible = !state.isLoading, enter = fadeIn(), exit = fadeOut()) {
                EventsContent(state, onEventClicked)
            }
        }
        BottomNavigationBar()
    }
}

@Composable
private fun EventsContent(
    state: SearchUiState,
    onEventClicked: (id: String) -> Unit
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.height(16.dp))

        FindYourMissionCard()

        NearestMissionsSection(state, onEventClicked)

        DonationsSection()

        InYourCitySection(state, onEventClicked)
    }
}

@Composable
fun FindYourMissionCard() {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(40.dp),
        shape = RoundedCornerShape(6.dp),
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    onClick = {},
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple()
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
}

@Composable
private fun NearestMissionsSection(
    state: SearchUiState,
    onEventClicked: (id: String) -> Unit
) {
    SectionTitle(text = "Nearest missions")

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(state.events, key = { it.id }) { event ->
            EventCard(
                event = event,
                onEventClicked = { onEventClicked(event.id) }
            )
        }
    }
}

@Composable
private fun DonationsSection() {
    SectionTitle(text = "Donations")

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
}

@Composable
private fun InYourCitySection(
    state: SearchUiState,
    onNavigateToEventDetails: (id: String) -> Unit
) {
    SectionTitle(text = "In your city")

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(state.events.asReversed(), key = { it.id }) { event ->
            EventCard(
                event = event,
                onEventClicked = { onNavigateToEventDetails(event.id) }
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
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
        color = PrimaryColor,
        modifier = Modifier
            .clip(metadataShape)
            .border(1.dp, PrimaryColor, metadataShape)
            .padding(vertical = 4.dp, horizontal = 8.dp)
    )
}

@Composable
fun SectionTitle(text: String) {
    Spacer(modifier = Modifier.height(24.dp))

    Text(
        fontFamily = eUkraineFontFamily,
        text = text,
        fontWeight = FontWeight.W500,
        color = OnSurfacePrimary,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp,
        modifier = Modifier.padding(start = 16.dp)
    )

    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun BottomNavigationBar() {
    BottomNavigation(
        modifier = Modifier
            .height(72.dp),
        backgroundColor = Color.White,
        elevation = 16.dp
    ) {
        BottomNavigationItem(
            text = "Missions",
            color = PrimaryColor,
            icon = R.drawable.ic_missions
        )
        BottomNavigationItem(
            text = "Your level",
            color = OnSurfacePrimary,
            icon = R.drawable.ic_level
        )
        BottomNavigationItem(
            text = "Profile",
            color = OnSurfacePrimary,
            icon = R.drawable.ic_user
        )
    }
}

@Composable
fun RowScope.BottomNavigationItem(text: String, color: Color, icon: Int) {
    Column(
        modifier = Modifier
            .weight(1f)
            .clickable(
                onClick = {},
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false)
            )
            .padding(top = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.height(20.dp)
        )
        Text(
            fontFamily = eUkraineFontFamily,
            text = text,
            color = color,
            fontSize = 12.sp,
            fontWeight = FontWeight.W300
        )
    }
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
                .clickable(
                    onClick = {},
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple()
                )
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