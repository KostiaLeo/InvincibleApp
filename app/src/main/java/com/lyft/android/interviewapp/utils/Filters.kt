package com.lyft.android.interviewapp.utils


data class EventFilter(val name: String, val isSelected: Boolean, val id: Int)

val defaultFilters by lazy {
    listOf(
        EventFilter("Цього тижня", false, 0),
        EventFilter("Цього місяця", false, 1),
        EventFilter("1-2 год", false, 2),
        EventFilter("3-4 год", false, 3),
    )
}