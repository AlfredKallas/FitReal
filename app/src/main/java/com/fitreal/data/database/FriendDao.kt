package com.fitreal.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendDao {
    @Query("SELECT friendId FROM friends WHERE userId = :userId")
    fun getFriendIds(userId: String): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFriends(friends: List<Friend>)
}