package com.lyft.android.interviewapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "main")
data class MainEntity(
    @PrimaryKey val id: Int
)