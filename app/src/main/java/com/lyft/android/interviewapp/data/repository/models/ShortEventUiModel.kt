package com.lyft.android.interviewapp.data.repository.models

import androidx.annotation.DrawableRes

data class ShortEventUiModel(
    val id: String,
    @DrawableRes val iconResourceId: Int,
    val dateTime: String = "", // 12.02.2023 12:00
    val gamePoints: String = "", // +30G
    val name: String = "",
    val location: String = "",
    val volunteersCount: String = "", // 12/50
    val donationsCount: String = "", // 2400/3000
)
