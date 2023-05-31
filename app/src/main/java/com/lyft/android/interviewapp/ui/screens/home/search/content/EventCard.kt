package com.lyft.android.interviewapp.ui.screens.home.search.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lyft.android.interviewapp.R
import com.lyft.android.interviewapp.data.repository.models.ShortEventUiModel
import com.lyft.android.interviewapp.ui.theme.AppTheme
import com.lyft.android.interviewapp.ui.theme.EventBorderColor
import com.lyft.android.interviewapp.ui.theme.HintTextColor
import com.lyft.android.interviewapp.ui.theme.OnSurfacePrimary
import com.lyft.android.interviewapp.ui.theme.eUkraineFontFamily

@Composable
fun EventCard(event: ShortEventUiModel, onEventClicked: () -> Unit) {
    Surface(
        border = BorderStroke(1.dp, EventBorderColor),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = onEventClicked,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple()
                )
                .padding(all = 16.dp)
                .padding(bottom = 4.dp)
        ) {
            Text(
                fontFamily = eUkraineFontFamily,
                text = event.name,
                fontSize = 14.sp,
                letterSpacing = 0.1.sp,
                color = OnSurfacePrimary,
                fontWeight = FontWeight.W500,
            )
            Spacer(modifier = Modifier.height(4.dp))
            EventMetadata(address = event.location, dateTime = event.dateTime)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
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

val previewEvent = ShortEventUiModel(
    id = "id",
    dateTime = "12:00 - 14:00 • 24 Травня 2023",
    name = "Розчищення доріг від завалів",
    location = "Вінниця, вул. Київська 65",
    volunteersCount = "12/50"
)

@Composable
fun EventMetadata(
    address: String,
    dateTime: String
) {
    Text(
        text = address,
        fontWeight = FontWeight.W300,
        fontSize = 13.sp,
        color = HintTextColor
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = dateTime,
        fontWeight = FontWeight.W300,
        fontSize = 13.sp,
        color = HintTextColor
    )
}

@Preview
@Composable
fun EventCardPreview() {
    AppTheme {
        EventCard(
            event = previewEvent,
            onEventClicked = {}
        )
    }
}