package com.lyft.android.interviewapp.data.remote.models

data class MyInfoResponse(
    val name: String,
    val cityCode: Int,
    val experience: Int,
    val currentPuzzleId: Int,
    val currentPuzzlePieces: List<Int>,
    val puzzleSlots: Int = 0,
    val completedPuzzlesIds: List<Int>,
    val level: Int,
    val statistics: Statistics
)

data class Statistics(val statList: List<Stat> = emptyList())

data class Stat(val name: String, val value: String)
