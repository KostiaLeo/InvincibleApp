@file:OptIn(ExperimentalMaterialApi::class)

package com.lyft.android.interviewapp.ui.screens.home.profile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lyft.android.interviewapp.R
import com.lyft.android.interviewapp.ui.theme.AppTheme
import com.lyft.android.interviewapp.ui.theme.HintTextColor
import com.lyft.android.interviewapp.ui.theme.LightGrayBackgroundColor
import com.lyft.android.interviewapp.ui.theme.TextColor

@Composable
fun ProfileScreen(
    state: ProfileUiState,
    onQrCodeClicked: () -> Unit,
    onEditProfileClicked: () -> Unit,
    onAboutClicked: () -> Unit,
    onLogoutClicked: () -> Unit,
    onRefresh: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                contentColor = TextColor,
                title = {
                    Text(text = "Профіль", fontSize = 14.sp, fontWeight = FontWeight.W500)
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
        }
    ) { contentPaddings ->
        val pullRefreshState = rememberPullRefreshState(
            refreshing = state.isLoading,
            onRefresh = {
                Log.d("REFRESH", "Refreshing profile 1")
                onRefresh()
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPaddings)
        ) {
            Column(
                modifier = Modifier
                    .background(LightGrayBackgroundColor)
                    .fillMaxSize()
                    .pullRefresh(state = pullRefreshState)
                    .padding(vertical = 24.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = state.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Волонтер",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W300,
                    color = HintTextColor
                )
                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    shape = RoundedCornerShape(8.dp),
                    backgroundColor = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = onEditProfileClicked)
                                .padding(vertical = 16.dp, horizontal = 20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.edit),
                                contentDescription = "Edit profile",
                                tint = TextColor
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Редагувати профіль",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W300
                            )
                        }
                        Divider(
                            modifier = Modifier
                                .height(1.dp)
                                .padding(start = 56.dp, end = 20.dp),
                            color = Color(0xFFE5E7EB)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = onAboutClicked)
                                .padding(vertical = 16.dp, horizontal = 20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.about),
                                contentDescription = "About",
                                tint = TextColor
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Про застосунок та авторів",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W300
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Вийти з профілю",
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .clickable(onClick = onLogoutClicked)
                        .padding(vertical = 8.dp, horizontal = 32.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                    color = Color(0xFFE02424),
                )
            }
            PullRefreshIndicator(
                refreshing = state.isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    AppTheme {
        ProfileScreen(
            state = ProfileUiState(
                isLoading = true,
                name = "Костянтин"
            ),
            onQrCodeClicked = {},
            onEditProfileClicked = {},
            onAboutClicked = {},
            onLogoutClicked = {},
            onRefresh = {}
        )
    }
}