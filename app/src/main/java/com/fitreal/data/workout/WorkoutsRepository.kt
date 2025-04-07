package com.fitreal.data.workout

import kotlinx.coroutines.flow.Flow

interface WorkoutsRepository {
    suspend fun getWorkoutsfeed(): Flow<List<Workout>>

    suspend fun getCurrentUserWorkouts(): Flow<List<Workout>>

    suspend fun addWorkOutForCurrentUser(workout: Workout)
}