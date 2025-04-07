package com.fitreal.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "populatedDatabase")
data class PopulatedDatabase(
    @PrimaryKey val isDatabasePopulated: Boolean
)
