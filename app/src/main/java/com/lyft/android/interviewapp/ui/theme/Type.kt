package com.lyft.android.interviewapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.lyft.android.interviewapp.R

val eUkraineFontFamily = FontFamily(
    Font(R.font.e_ukraine_bold, FontWeight.Bold),
    Font(R.font.e_ukraine_light, FontWeight.Light),
    Font(R.font.e_ukraine_medium, FontWeight.Medium),
    Font(R.font.e_ukraine_regular, FontWeight.Normal),
    Font(R.font.e_ukraine_thin, FontWeight.Thin),
    Font(R.font.e_ukraine_ultra_light, FontWeight.ExtraLight),
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = eUkraineFontFamily
//    body1 = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp
//    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)