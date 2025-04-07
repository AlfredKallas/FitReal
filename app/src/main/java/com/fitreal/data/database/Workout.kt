package com.fitreal.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey val id: String,
    val userId: String,
    val title: String,
    val duration: String,
    val imageUrl: String
)