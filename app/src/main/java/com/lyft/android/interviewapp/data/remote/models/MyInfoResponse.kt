package com.lyft.android.interviewapp.data.remote.models

data class MyInfoResponse(
    val name: String,
    val cityCode: Int,
    val experience: Int,
    val puzzleSlotsCount: Int,
    val currentPuzzleId: Int,
    val currentPuzzlePieces: List<Int>,
    val completedPuzzlesIds: List<Int>,
    val level: Int,
    val statistics: List<Stat>
)

data class Statistics(val statsList: List<Stat> = emptyList())

data class Stat(val name: String, val value: String)
