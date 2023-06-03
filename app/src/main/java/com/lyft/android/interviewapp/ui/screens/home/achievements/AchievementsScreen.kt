@file:OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterialApi::class)

package com.lyft.android.interviewapp.ui.screens.home.achievements

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lyft.android.interviewapp.R
import com.lyft.android.interviewapp.data.remote.models.Stat
import com.lyft.android.interviewapp.ui.theme.AppTheme
import com.lyft.android.interviewapp.ui.theme.HintTextColor
import com.lyft.android.interviewapp.ui.theme.LightGrayBackgroundColor
import com.lyft.android.interviewapp.ui.theme.PrimaryColor
import com.lyft.android.interviewapp.ui.theme.TextColor
import com.lyft.android.interviewapp.utils.dashedBorder

@Composable
fun AchievementsScreen(
    state: AchievementsUiState,
    onQrCodeClicked: () -> Unit,
    onRefresh: () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(
            backgroundColor = Color.White,
            contentColor = TextColor,
            title = {
                Text(text = "Досягнення", fontSize = 14.sp, fontWeight = FontWeight.W500)
            },
            actions = {
                IconButton(onClick = onQrCodeClicked) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_qr_code),
                        contentDescription = "QR code",
                        tint = TextColor
                    )
                }
            }
        )
    }) { contentPaddings ->
        val pullRefreshState = rememberPullRefreshState(
            refreshing = state.isLoading,
            onRefresh = onRefresh
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPaddings)
                .pullRefresh(state = pullRefreshState)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 24.dp,
                    bottom = 16.dp
                )
            ) {
                item {
                    Level(state)
                }
                if (state.statistics.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
                statistics(state.statistics)
                item {
                    Spacer(modifier = Modifier.height(40.dp))
                }
                puzzlesCollection(state.puzzlesCollection)
            }

            PullRefreshIndicator(
                refreshing = state.isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun Level(state: AchievementsUiState) {
    Text(
        text = "${state.level} рівень",
        fontSize = 18.sp,
        fontWeight = FontWeight.W500
    )
    Spacer(modifier = Modifier.height(16.dp))
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .height(8.dp)
                .border(1.dp, Color(0xFFC3DDFD), RoundedCornerShape(4.dp))
                .fillMaxWidth()
        )
        LinearProgressIndicator(
            progress = (state.experience % 100) / 100f,
            color = PrimaryColor,
            backgroundColor = Color.Transparent,
            strokeCap = StrokeCap.Round,
            modifier = Modifier
                .height(8.dp)
                .fillMaxWidth()
        )
    }
    Spacer(modifier = Modifier.height(4.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Прогрес",
            fontSize = 13.sp,
            fontWeight = FontWeight.W300,
            color = HintTextColor
        )
        val exp =
            if (state.experience % 100 == 0 && state.experience != 0) 100 else state.experience % 100
        Text(text = "${exp}/100", fontSize = 13.sp, fontWeight = FontWeight.W300)
    }
}

fun LazyListScope.statistics(statistics: List<Stat>) {
    val chunked = statistics.chunked(2)
    Log.d("ACHIEVEMENTS", chunked.joinToString { it.toString() })
    chunked.forEachIndexed { index, stats ->

        if (index != 0) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                stats.forEach { stat ->
                    Stat(modifier = Modifier.weight(1f), stat)
                }
            }
        }
    }
}

@Composable
fun Stat(modifier: Modifier, stat: Stat) {
    Surface(
        modifier = modifier,
        border = BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = stat.value,
                fontSize = 36.sp,
                fontWeight = FontWeight.W500,
                color = PrimaryColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stat.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.W300,
                color = HintTextColor
            )
        }
    }
}

fun LazyListScope.puzzlesCollection(puzzlesCollection: List<PuzzleCollectionItem>) {
    item {
        Text(
            text = "Моя колекція",
            fontSize = 18.sp,
            fontWeight = FontWeight.W500
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
    puzzlesCollection.forEachIndexed { index, item ->
        if (index != 0) {
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
        item {
            when (item) {
                is PuzzleCollectionItem.NoItems -> {
                    NoPuzzlesOpened()
                }

                is PuzzleCollectionItem.EmptySlot -> {
                    EmptySlotItem()
                }

                is PuzzleCollectionItem.CurrentPuzzle -> {
                    CurrentPuzzleContent(item)
                }

                is PuzzleCollectionItem.CompletedPuzzle -> {
                    CompletedPuzzleContent(item)
                }

                is PuzzleCollectionItem.Footer -> {
                    FooterContent()
                }
            }
        }
    }
}

@Composable
fun CurrentPuzzleContent(item: PuzzleCollectionItem.CurrentPuzzle) {
    Box(modifier = Modifier.size(344.dp, 394.dp)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .dashedBorder(
                    1.dp,
                    SolidColor(PrimaryColor),
                    RoundedCornerShape(8.dp),
                    12.dp,
                    12.dp
                )
        )
        val itemAlignments = remember(item.piecesUrls.size) {
            listOf(
                Alignment.TopStart,
                Alignment.TopEnd,
                Alignment.BottomStart,
                Alignment.BottomEnd
            )
        }
        val sizes = remember {
            listOf(
                DpSize(208.dp, 233.dp),
                DpSize(172.dp, 197.dp),
                DpSize(208.dp, 197.dp),
                DpSize(172.dp, 233.dp),
            )
        }
        val params = itemAlignments.zip(sizes)

        item.pieces
            .map {
                params[it]
            }
            .zip(item.piecesUrls) { (alignment, size), url ->
                Triple(alignment, size, url)
            }
            .forEach { (alignment, size, url) ->
                GlideImage(
                    modifier = Modifier
                        .align(alignment)
                        .size(size),
                    model = url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }

    }
}

@Composable
fun CompletedPuzzleContent(item: PuzzleCollectionItem.CompletedPuzzle) {
    Column(modifier = Modifier.fillMaxWidth()) {
        GlideImage(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .height(394.dp),
            model = item.puzzleUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = item.puzzleName, fontSize = 14.sp, fontWeight = FontWeight.W500)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.author,
            fontSize = 13.sp,
            fontWeight = FontWeight.W300,
            color = HintTextColor
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun FooterContent() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(154.dp),
        color = LightGrayBackgroundColor,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.no_puzzles),
                contentDescription = "No puzzles",
                tint = HintTextColor
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Підвищуй свій рівень, щоб відкрити більше рамок для картин в колекції",
                fontSize = 13.sp,
                fontWeight = FontWeight.W300,
                color = HintTextColor,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                lineHeight = 19.5.sp
            )
        }
    }
}

@Composable
fun EmptySlotItem() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(394.dp)
            .dashedBorder(1.dp, SolidColor(PrimaryColor), RoundedCornerShape(8.dp), 12.dp, 12.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.new_slot),
                contentDescription = "New slot",
                tint = HintTextColor
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Відкрита нова рамка",
                fontSize = 13.sp,
                fontWeight = FontWeight.W500,
                color = HintTextColor,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Підвищуй свій рівень, щоб зібрати картину українського художника",
                fontSize = 13.sp,
                fontWeight = FontWeight.W300,
                color = HintTextColor,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                lineHeight = 19.5.sp
            )
        }
    }
}

@Composable
fun NoPuzzlesOpened() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(394.dp),
        color = LightGrayBackgroundColor,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.no_puzzles),
                contentDescription = "No puzzles",
                tint = HintTextColor
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Ви ще не відкрили жодної рамки",
                fontSize = 13.sp,
                fontWeight = FontWeight.W500,
                color = HintTextColor,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Підвищуй свій рівень, щоб відкрити більше рамок для картин в колекції",
                fontSize = 13.sp,
                fontWeight = FontWeight.W300,
                color = HintTextColor,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                lineHeight = 19.5.sp
            )
        }
    }
}

@Preview
@Composable
fun AchievementsScreenPreview() {
    AppTheme {
        AchievementsScreen(
            state = AchievementsUiState(
                isLoading = false,
                level = 1,
                experience = 137,
                statistics = listOf(
                    Stat("Завершених місій", "15"),
                    Stat("Годин витрачено", "48"),
                    Stat("Завершених місій", "15"),
                ),
                puzzlesCollection = listOf(
                    PuzzleCollectionItem.EmptySlot, PuzzleCollectionItem.Footer,
                )
            ),
            onQrCodeClicked = {},
            onRefresh = {}
        )
    }
}