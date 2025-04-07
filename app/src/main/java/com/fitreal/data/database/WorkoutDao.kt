package com.fitreal.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workouts WHERE userId IN (:friendIds)")
    fun getFriendWorkouts(friendIds: List<String>): Flow<List<Workout>>

    @Query("SELECT * FROM workouts")
    fun getAllWorkouts(): Flow<List<Workout>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorkouts(workouts: List<Workout>)
}