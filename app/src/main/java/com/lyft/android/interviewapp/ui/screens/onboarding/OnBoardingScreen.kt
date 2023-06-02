package com.lyft.android.interviewapp.ui.screens.onboarding

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lyft.android.interviewapp.ui.theme.AppTheme
import com.lyft.android.interviewapp.ui.theme.LightGrayBackgroundColor
import com.lyft.android.interviewapp.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OnBoardingScreen(
    state: OnBoardingUiState,
    onNameChanged: (String) -> Unit,
    onCitySelected: (City) -> Unit,
    onCreateAccountClicked: () -> Unit,
    onCompleted: (name: String) -> Unit,
    onCloseClicked: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        (context as AppCompatActivity).run {
            window.navigationBarColor = getColor(android.R.color.white)
            window.statusBarColor = getColor(android.R.color.white)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                navigationIcon = {
                    Row {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = if (state.displayMode == DisplayMode.EDIT_ACCOUNT) {
                                Icons.Default.ArrowBack
                            } else {
                                Icons.Default.Close
                            },
                            contentDescription = null,
                            modifier = Modifier.clickable(onClick = onCloseClicked)
                        )
                    }
                },
                title = {
                    if (state.displayMode == DisplayMode.EDIT_ACCOUNT) {
                        Text(text = "Профіль", fontWeight = FontWeight.W500, fontSize = 14.sp)
                    }
                }
            )
        },
        content = { paddingValues ->
            val pullRefreshState = rememberPullRefreshState(
                refreshing = state.showProgress,
                onRefresh = {}
            )
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                        .pullRefresh(
                            state = pullRefreshState,
                            enabled = false
                        )
                ) {
                    Text(
                        text = if (state.displayMode == DisplayMode.EDIT_ACCOUNT) {
                            "Редагувати профіль"
                        } else {
                            "Створити акаунт"
                        },
                        fontWeight = FontWeight.W500,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    NameInput(state, onNameChanged)
                    Spacer(modifier = Modifier.height(24.dp))
                    CitySelector(state, onCitySelected)
                }
                PullRefreshIndicator(
                    refreshing = state.showProgress,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        },
        bottomBar = {
            BottomAppBar(
                elevation = 0.dp,
                backgroundColor = Color.White,
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                Button(
                    enabled = state.isReadyToRegister && !state.showProgress,
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = PrimaryColor,
                        contentColor = Color.White
                    ),
                    onClick = onCreateAccountClicked
                ) {
                    Text(
                        text = if (state.displayMode == DisplayMode.EDIT_ACCOUNT) {
                            "Зберегти"
                        } else {
                            "Створити акаунт"
                        },
                        fontWeight = FontWeight.W500,
                        fontSize = 14.sp
                    )
                }
            }
        }
    )

    if (state.isAccountAlterationCompleted) {
        LaunchedEffect(Unit) {
            onCompleted(state.name)
        }
    }
}

@Composable
private fun NameInput(
    state: OnBoardingUiState,
    onNameChanged: (String) -> Unit
) {
    Text(text = "Імʼя", fontWeight = FontWeight.W500, fontSize = 14.sp)
    Spacer(modifier = Modifier.height(8.dp))

    var isNameFieldFocused by remember { mutableStateOf(false) }
    val nameBorderColor by animateColorAsState(if (isNameFieldFocused) PrimaryColor else Color.Transparent)
    TextField(
        value = state.name,
        onValueChange = onNameChanged,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = LightGrayBackgroundColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, nameBorderColor, RoundedCornerShape(8.dp))
            .onFocusChanged { focusState ->
                isNameFieldFocused = focusState.hasFocus
            },

        placeholder = {
            if (state.name.isEmpty()) {
                Text(
                    text = "Введіть ваше імʼя",
                    fontWeight = FontWeight.W300,
                    fontSize = 14.sp,
                    modifier = Modifier,
                    textAlign = TextAlign.Center
                )
            }
        }
    )
}

@Composable
private fun ColumnScope.CitySelector(
    state: OnBoardingUiState,
    onCitySelected: (City) -> Unit
) {
    Text(text = "Місто", fontWeight = FontWeight.W500, fontSize = 14.sp)
    Spacer(modifier = Modifier.height(8.dp))

    val cities = remember { Cities() }

    var isCityFieldFocused by remember { mutableStateOf(false) }
    val cityBorderColor by animateColorAsState(
        if (isCityFieldFocused) PrimaryColor else Color.Transparent,
        tween(100)
    )
    var isCityDropdownExpanded by remember { mutableStateOf(false) }

    TextField(
        value = state.selectedCity?.name.orEmpty(),
        readOnly = true,
        onValueChange = {},
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = LightGrayBackgroundColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, cityBorderColor, MaterialTheme.shapes.medium)
            .onFocusChanged { focusState ->
                isCityFieldFocused = focusState.isFocused
            },
        placeholder = {
            if (state.selectedCity == null) {
                Text(
                    text = "Оберіть місто зі списку",
                    fontWeight = FontWeight.W300,
                    fontSize = 14.sp,
                    modifier = Modifier,
                    textAlign = TextAlign.Center
                )
            }
        },
        trailingIcon = {
            val dropdownIconRotateDegrees by animateFloatAsState(if (isCityDropdownExpanded) 180f else 0f)
            Icon(
                modifier = Modifier
                    .rotate(dropdownIconRotateDegrees)
                    .clickable {
                        isCityDropdownExpanded = !isCityDropdownExpanded
                    },
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null
            )
        }
    )

    if (isCityDropdownExpanded) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = false),
            elevation = 4.dp
        ) {
            LazyColumn {
                items(cities, key = { it.code }) { city ->
                    Text(
                        text = city.name,
                        fontWeight = FontWeight.W300,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onCitySelected(city)
                                isCityDropdownExpanded = false
                            }
                            .padding(vertical = 12.dp, horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

/*

@Composable
private fun CitySelector(
    state: OnBoardingUiState,
    onCitySelected: (City?) -> Unit
) {
    Text(text = "Місто", fontWeight = FontWeight.W500, fontSize = 14.sp)
    Spacer(modifier = Modifier.height(8.dp))

    val cities = remember { Cities() }
    var query by remember { mutableStateOf("") }
    val matchedCities by remember(query) {
        derivedStateOf {
            if (state.selectedCity != null || query.isBlank()) {
                emptyList()
            } else {
                cities.filter { it.name.contains(query, ignoreCase = true) }
            }
        }
    }

    var isCityFieldFocused by remember { mutableStateOf(false) }
    val cityBorderColor by animateColorAsState(if (isCityFieldFocused) PrimaryColor else Color.Transparent)
    val cityFocusRequester = remember { FocusRequester() }

    TextField(
        value = query,
        onValueChange = {
            query = it
            if (state.selectedCity != null) {
                onCitySelected(null)
            }
        },
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = TextFieldBackgroundColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, cityBorderColor, MaterialTheme.shapes.medium)
            .onFocusChanged { focusState ->
                isCityFieldFocused = focusState.isFocused
            }
            .focusRequester(cityFocusRequester),
        placeholder = {
            if (query.isEmpty()) {
                Text(
                    text = "Оберіть місто зі списку",
                    fontWeight = FontWeight.W300,
                    fontSize = 14.sp,
                    modifier = Modifier,
                    textAlign = TextAlign.Center
                )
            }
        }
    )

    if (matchedCities.isNotEmpty()) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
        ) {
            LazyColumn {
                items(matchedCities, key = { it.code }) { city ->
                    Text(
                        text = city.name,
                        fontWeight = FontWeight.W300,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(vertical = 12.dp, horizontal = 16.dp)
                            .clickable {
                                query = city.name
                                onCitySelected(city)
                            }
                    )
                }
            }
        }
    }
}
*/

@Preview
@Composable
fun OnBoardingPreview() {
    AppTheme {
        OnBoardingScreen(
            OnBoardingUiState(displayMode = DisplayMode.EDIT_ACCOUNT),
            {},
            {},
            {},
            {},
            {},
        )
    }
}