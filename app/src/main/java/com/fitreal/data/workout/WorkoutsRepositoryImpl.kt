package com.fitreal.data.workout

import com.fitreal.data.account.AccountRepository
import com.fitreal.data.database.FriendDao
import com.fitreal.data.database.WorkoutDao
import com.fitreal.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class WorkoutsRepositoryImpl @Inject constructor(
    private val accountRepository: AccountRepository,
    private val friendDao: FriendDao,
    private val workoutDao: WorkoutDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : WorkoutsRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getWorkoutsfeed(): Flow<List<Workout>> {
        return workoutDao.getAllWorkouts()
            .map { list -> list.map { Workout(imageUrl = it.imageUrl, title = it.title, duration = it.duration) } }
            .flowOn(dispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getCurrentUserWorkouts(): Flow<List<Workout>> {
        val userId = accountRepository.currentUserId
        return workoutDao.getFriendWorkouts( listOf(userId))
            .map { list -> list.map { Workout(imageUrl = it.imageUrl, title = it.title, duration = it.duration) } }
            .flowOn(dispatcher)
    }

    override suspend fun addWorkOutForCurrentUser(workout: Workout) {
        val userId = accountRepository.currentUserId
        val workoutId = UUID.randomUUID().toString()
        val workoutRecord = com.fitreal.data.database.Workout(
            id = workoutId,
            userId = userId,
            title = workout.title,
            duration = workout.duration,
            imageUrl = workout.imageUrl
        )
        workoutDao.insertWorkouts(listOf(workoutRecord))
    }


}