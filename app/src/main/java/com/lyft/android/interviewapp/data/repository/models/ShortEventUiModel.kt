package com.lyft.android.interviewapp.data.repository.models

data class ShortEventUiModel(
    val id: String,
    val dateTime: String = "", // 12:00 - 14:00 • 12 Травня 2023
    val name: String = "",
    val location: String = "",
    val volunteersCount: String = "", // 12/50
)
