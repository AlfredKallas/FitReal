package com.fitreal.data.database

import androidx.room.Entity

@Entity(tableName = "friends", primaryKeys = ["userId", "friendId"])
data class Friend(
    val userId: String,
    val friendId: String
)