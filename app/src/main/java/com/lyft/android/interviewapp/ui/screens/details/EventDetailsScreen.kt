package com.lyft.android.interviewapp.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lyft.android.interviewapp.R
import com.lyft.android.interviewapp.data.remote.models.OrganizerInfo
import com.lyft.android.interviewapp.data.repository.models.EventDetailsUiModel
import com.lyft.android.interviewapp.data.repository.models.EventStatus
import com.lyft.android.interviewapp.data.repository.models.RegisterButtonConfigs
import com.lyft.android.interviewapp.data.repository.models.RegistrationStatus
import com.lyft.android.interviewapp.ui.screens.home.search.content.EventMetadata
import com.lyft.android.interviewapp.ui.theme.AppTheme
import com.lyft.android.interviewapp.ui.theme.HintTextColor
import com.lyft.android.interviewapp.ui.theme.LightGrayBackgroundColor
import com.lyft.android.interviewapp.ui.theme.TextColor

@Composable
fun EventDetailsScreen(
    state: PlaceDetailsUiState,
    onRegisterClicked: () -> Unit,
    onGoBackClicked: () -> Unit,
    onQrCodeClicked: () -> Unit,
    callOrganizerClicked: (phoneNumber: String) -> Unit,
    onRefresh: () -> Unit
) {
    Scaffold(
        topBar = {
            EventDetailsTopBar(onGoBackClicked, onQrCodeClicked)
        },
        content = { paddingValues ->
            EventDetailsContent(paddingValues, state, callOrganizerClicked, onRefresh)
        },
        bottomBar = {
            EventDetailsBottomBar(state, onRegisterClicked)
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun EventDetailsContent(
    paddingValues: PaddingValues,
    state: PlaceDetailsUiState,
    callOrganizerClicked: (phoneNumber: String) -> Unit,
    onRefresh: () -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isLoading,
        onRefresh = onRefresh
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = state.details.name,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            EventMetadata(address = state.details.location, dateTime = state.details.dateTime)
            ContentDivider()
            Text(
                text = "Про місію",
                fontWeight = FontWeight.W500,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            ExpandableText(
                text = state.details.description,
                minimizedMaxLines = 4,
                style = TextStyle(
                    fontWeight = FontWeight.W400,
                    fontSize = 14.sp,
                    color = HintTextColor,
                    letterSpacing = 0.6.sp,
                    lineHeight = 20.sp
                )
            )
            ContentDivider()
            Organizer(state.details.organizer, callOrganizerClicked)
            ContentDivider()

            Text(text = "Фотозвіт", fontWeight = FontWeight.W500, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(16.dp))

            if (state.details.photos.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(288.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = LightGrayBackgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Фотозвіт буде завантажено організатором після завершення місії",
                        fontWeight = FontWeight.W300,
                        fontSize = 13.sp,
                        color = HintTextColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 30.dp)
                    )
                }
            } else {
                PhotosCarousel(state.details.photos)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
        PullRefreshIndicator(
            refreshing = state.isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PhotosCarousel(photos: List<String>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(photos.size) { index ->
            GlideImage(
                modifier = Modifier
                    .size(DpSize(216.dp, 288.dp))
                    .background(LightGrayBackgroundColor, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp)),
                model = photos[index],
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
        }
    }
}

@Composable
fun Organizer(
    organizer: OrganizerInfo,
    callOrganizerClicked: (phoneNumber: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    text = organizer.name ?: "Волонтер",
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp
                )
                Text(
                    text = "Організатор",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W300
                )
            }
            if (!organizer.phoneNumber.isNullOrBlank()) {
                Image(
                    modifier = Modifier
                        .background(
                            color = LightGrayBackgroundColor,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(11.dp)
                        .clickable {
                            callOrganizerClicked(organizer.phoneNumber)
                        },
                    painter = painterResource(R.drawable.ic_phone),
                    contentDescription = null
                )
            }
        }

        if (!organizer.description.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = organizer.description,
                fontSize = 13.sp,
                fontWeight = FontWeight.W300,
                color = HintTextColor,
                lineHeight = 20.sp
            )
        }

    }
}

@Composable
fun ContentDivider() {
    Spacer(modifier = Modifier.height(24.dp))
    Divider()
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    text: String,
    minimizedMaxLines: Int,
    style: TextStyle
) {
    var expanded by remember { mutableStateOf(false) }
    var hasVisualOverflow by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        Text(
            text = text,
            maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
            onTextLayout = { hasVisualOverflow = it.hasVisualOverflow },
            style = style
        )
        if (hasVisualOverflow) {
            Row(
                modifier = Modifier.align(Alignment.BottomEnd),
                verticalAlignment = Alignment.Bottom
            ) {
                val lineHeightDp: Dp = with(LocalDensity.current) { style.lineHeight.toDp() }
                Spacer(
                    modifier = Modifier
                        .width(48.dp)
                        .height(lineHeightDp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color.Transparent, Color.White)
                            )
                        )
                )
                Text(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(start = 4.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = { expanded = !expanded }
                        ),
                    text = "Більше",
                    color = HintTextColor,
                    style = TextStyle(
                        fontWeight = FontWeight.W500,
                        fontSize = 13.sp,
                        letterSpacing = 0.6.sp,
                        textDecoration = TextDecoration.Underline
                    )
                )
            }
        }
    }
}

@Composable
fun EventDetailsTopBar(
    onGoBackClicked: () -> Unit,
    onQrCodeClicked: () -> Unit
) {
    TopAppBar(
        backgroundColor = Color.White,
        contentColor = TextColor,
        title = {
            Text(text = "Пошук", fontSize = 14.sp, fontWeight = FontWeight.W500)
        },
        navigationIcon = {
            IconButton(onClick = onGoBackClicked) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = onQrCodeClicked) {
                Icon(
                    painter = painterResource(R.drawable.ic_qr_code),
                    contentDescription = "QR code",
                    tint = TextColor
                )
            }
        }
    )
}

@Composable
fun EventDetailsBottomBar(
    state: PlaceDetailsUiState,
    onRegisterClicked: () -> Unit
) {
    Column {
        Divider()
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_user),
                    tint = TextColor,
                    contentDescription = null
                )
                Text(
                    text = state.details.volunteersCount,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500
                )
            }

            val configs = state.details.buttonConfigs

            Box(
                modifier = Modifier
                    .size(200.dp, 48.dp)
                    .background(
                        color = configs.color,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .clip(RoundedCornerShape(6.dp))
                    .run {
                        if (configs.clickable) {
                            clickable(onClick = onRegisterClicked)
                        } else {
                            this
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = configs.text,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                    color = configs.textColor
                )
            }
        }
    }
}

@Composable
@Preview
fun EventDetailsScreenPreview() {
    AppTheme {
        EventDetailsScreen(
            state = PlaceDetailsUiState(
                details = EventDetailsUiModel(
                    name = "Event name",
                    organizer = OrganizerInfo(
                        "Kostia",
                        "+3800971231231",
                        "Ми допомагаємо армії України та громадянам, " +
                                "які постраждали від російської агресії. " +
                                "Допомогти може кожен!"
                    ),
                    location = "Vinnytsia",
                    dateTime = "12:00 - 14:00 • 24 Травня 2023",
                    description = (
                            "Волонтерська місія зосереджена на розчищенні доріг від уламків " +
                                    "та завалів після ракетного обстрілу. " +
                                    "Однією з наших найважливіших метою є відновлення"
                            ).repeat(2),
                    volunteersCount = "12/50",
                    buttonConfigs = RegisterButtonConfigs.fromEventStatus(
                        EventStatus.ACTIVE,
                        RegistrationStatus.AVAILABLE
                    )
                ),
                isLoading = false
            ),
            onRegisterClicked = {},
            onGoBackClicked = {},
            onQrCodeClicked = {},
            callOrganizerClicked = {},
            {}
        )
    }
}