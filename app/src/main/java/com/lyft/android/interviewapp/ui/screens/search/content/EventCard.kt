package com.lyft.android.interviewapp.ui.screens.search.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
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
import com.lyft.android.interviewapp.ui.theme.*

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
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(event.timeRange, event.date, event.gamePoints).forEach {
                    EventMetadata(metadata = it)
                }
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

val previewEvent = ShortEventUiModel(
    id = "id",
    timeRange = "12:00 - 14:00",
    date = "24 Травня",
    gamePoints = "+30G",
    name = "Розчищення доріг від завалів",
    location = "Вінниця, вул. Київська 65",
    volunteersCount = "12/50"
)

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