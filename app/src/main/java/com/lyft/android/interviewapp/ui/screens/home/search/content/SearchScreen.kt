package com.lyft.android.interviewapp.ui.screens.home.search.content

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lyft.android.interviewapp.R
import com.lyft.android.interviewapp.ui.screens.home.search.SearchUiState
import com.lyft.android.interviewapp.ui.screens.onboarding.City
import com.lyft.android.interviewapp.ui.theme.*
import com.lyft.android.interviewapp.utils.EventFilter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
    state: SearchUiState,
    onEventClicked: (id: String) -> Unit,
    onFilterSelected: (filter: EventFilter) -> Unit,
    onQrCodeClicked: () -> Unit,
    onCitySelected: (city: City) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        EventsFiltersTopBar(
            state.availableFilters,
            title = {
                CitySelector(state, onCitySelected)
            },
            onQrCodeClicked,
            onFilterSelected
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(LightGrayBackgroundColor)
                .pullRefresh(
                    state = rememberPullRefreshState(state.isLoading, onRefresh = {}),
                    enabled = false
                ),
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
    }
}

@Composable
fun EventsFiltersTopBar(
    filters: List<EventFilter>,
    title: @Composable () -> Unit,
    onQrCodeClicked: () -> Unit,
    onFilterSelected: (filter: EventFilter) -> Unit
) {
    Surface(elevation = 8.dp) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                title()

                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = onQrCodeClicked) {
                    Icon(
                        painter = painterResource(R.drawable.ic_qr_code),
                        contentDescription = "QR code",
                        tint = TextColor
                    )
                }
            }

            FiltersSelector(
                filters = filters,
                onFilterSelected = onFilterSelected
            )
        }
    }
}

@Composable
private fun CitySelector(
    state: SearchUiState,
    onCitySelected: (city: City) -> Unit
) {
    var isCityDropdownExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .clickable {
                isCityDropdownExpanded = !isCityDropdownExpanded
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = state.selectedCity?.name ?: "Україна",
            fontSize = 18.sp,
            fontWeight = FontWeight.W500
        )
        Icon(
            painter = painterResource(R.drawable.dropdown_arrow),
            contentDescription = null
        )

        DropdownMenu(
            expanded = isCityDropdownExpanded,
            onDismissRequest = { isCityDropdownExpanded = false }
        ) {
            state.cities.forEach { city ->
                DropdownMenuItem(
                    onClick = {
                        isCityDropdownExpanded = false
                        onCitySelected(city)
                    }
                ) {
                    Text(
                        text = city.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W300
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FiltersSelector(filters: List<EventFilter>, onFilterSelected: (EventFilter) -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
    ) {
        items(filters, key = { it.id }) { filter ->
            FilterChip(
                onClick = { onFilterSelected(filter) },
                selected = filter.isSelected,
                colors = ChipDefaults.filterChipColors(
                    selectedBackgroundColor = PrimaryColor,
                    selectedContentColor = Color.White,
                    backgroundColor = LightGrayBackgroundColor,
                    contentColor = TextColor
                ),
                shape = RoundedCornerShape(6.dp),
                content = {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp),
                        text = filter.name,
                        fontSize = 13.sp,
                        letterSpacing = 0.4.sp,
                        fontWeight = FontWeight.W300
                    )
                },
                trailingIcon = {
                    if (filter.isSelected) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
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
                events = listOf(previewEvent, previewEvent.copy(id = "2")),
            ),
            onEventClicked = {},
            onFilterSelected = {},
            onQrCodeClicked = {},
            onCitySelected = {}
        )
    }
}