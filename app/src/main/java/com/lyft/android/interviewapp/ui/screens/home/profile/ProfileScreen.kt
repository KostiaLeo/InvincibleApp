package com.lyft.android.interviewapp.ui.screens.home.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
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
fun ProfileScreen(state: ProfileUiState, onQrCodeClicked: () -> Unit) {
    Scaffold(topBar = {
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
    }) { contentPaddings ->
        Column(
            modifier = Modifier
                .padding(contentPaddings)
                .background(LightGrayBackgroundColor)
                .fillMaxSize()
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                ) {

                }
            }
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    AppTheme {
        ProfileScreen(
            state = ProfileUiState(
                isLoading = false,
                name = "Костянтин"
            ),
            onQrCodeClicked = {}
        )
    }
}