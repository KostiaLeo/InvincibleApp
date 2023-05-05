package com.lyft.android.interviewapp.ui.screens.search.content

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lyft.android.interviewapp.R
import com.lyft.android.interviewapp.ui.screens.search.SearchUiState
import com.lyft.android.interviewapp.ui.theme.*

@Composable
fun SearchScreen(
    state: SearchUiState,
    onEventClicked: (id: String) -> Unit
) {
    val tabs = listOf("Вінниця", "Київ", "Миколаїв", "Херсон", "Одеса")
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Surface(elevation = 4.dp) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Add your content here

                    FindYourMissionCard(modifier = Modifier.weight(1f))

                    IconButton(
                        onClick = { /* Handle filter icon click */ },
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_filter),
                            contentDescription = "Filter",
                            tint = TextColor
                        )
                    }
                }

                // Add your TabRow composable here
                ScrollableTabRow(
                    backgroundColor = Color.White,
                    selectedTabIndex = selectedTabIndex,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = PrimaryColor
                        )
                    },
                    divider = {},
                    edgePadding = 16.dp
                ) {
                    tabs.forEachIndexed { index, title ->
                        val selected = selectedTabIndex == index
                        val fontWeight = if (selected) FontWeight.W500 else FontWeight.W300

                        Tab(
                            text = {
                                Text(
                                    text = title,
                                    fontWeight = fontWeight,
                                    letterSpacing = 0.2.sp
                                )
                            },
                            selectedContentColor = PrimaryColor,
                            unselectedContentColor = TabTextColor,
                            selected = selected,
                            onClick = { selectedTabIndex = index }
                        )
                    }
                }
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Add your items to the list here
            items(state.events) { event ->
                EventCard(
                    event = event,
                    onEventClicked = {
                        onEventClicked(event.id)
                    }
                )
            }
        }
    }
}

/*@Composable
fun SearchScreenLegacy(
    state: SearchUiState,
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
}*/

@Composable
private fun EventsContent(
    state: SearchUiState,
    onEventClicked: (id: String) -> Unit
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.height(16.dp))

        FindYourMissionCard()

        NearestMissionsSection(state, onEventClicked)

    }
}

@Composable
fun FindYourMissionCard(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        shape = RoundedCornerShape(8.dp),
        color = TextFieldBackgroundColor
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
                text = "Пошук місії",
                fontSize = 14.sp,
                letterSpacing = 0.4.sp,
                color = HintTextColor,
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

@Preview
@Composable
fun SearchScreenPreview() {
    AppTheme {
        SearchScreen(
            state = SearchUiState(
                isLoading = false,
                events = listOf(previewEvent)
            ),
            onEventClicked = {}
        )
    }
}