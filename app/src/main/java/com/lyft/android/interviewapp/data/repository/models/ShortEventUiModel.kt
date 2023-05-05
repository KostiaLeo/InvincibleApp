package com.lyft.android.interviewapp.data.repository.models

data class ShortEventUiModel(
    val id: String,
    val date: String = "", // 12.02.2023
    val timeRange: String = "", // 12:00 - 14:00
    val gamePoints: String = "", // +30G
    val name: String = "",
    val location: String = "",
    val volunteersCount: String = "", // 12/50
)
