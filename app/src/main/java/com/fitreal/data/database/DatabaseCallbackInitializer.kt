package com.fitreal.data.database

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Provider

class DatabaseCallbackInitializer(
    userProvider: Provider<UserDao>,
    workoutProvider: Provider<WorkoutDao>,
    friendProvider: Provider<FriendDao>,
    populatedDatabaseProvider: Provider<PopulatedDatabaseDao>
) : RoomDatabase.Callback() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val userProvider: WeakReference<Provider<UserDao>> = WeakReference(userProvider)
    private val workoutProvider: WeakReference<Provider<WorkoutDao>> = WeakReference(workoutProvider)
    private val friendProvider: WeakReference<Provider<FriendDao>> = WeakReference(friendProvider)
    private val populatedDatabaseProvider: WeakReference<Provider<PopulatedDatabaseDao>> = WeakReference(populatedDatabaseProvider)


    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        applicationScope.launch(Dispatchers.IO) {
            populateDatabase()
        }
    }

    fun populateDatabase() {
            val isDatabasePrepopulated = populatedDatabaseProvider.get()?.get()?.isDatabasePopulated() == true

            if (!isDatabasePrepopulated) {
                // Insert Users
                val users = listOf(
                    User("I54fxL5jjITOwjRl1jty68gcwzA2", "Alice", "alice@email.com", "https://example.com/alice.jpg"),
                    User("userId2", "Bob", "bob@email.com", "https://example.com/bob.jpg"),
                    User("userId3", "Charlie", "charlie@email.com", "https://example.com/charlie.jpg")
                )
                userProvider.get()?.get()?.insertUsers(users)

                // Insert Friends
                val friends = listOf(
                    Friend("I54fxL5jjITOwjRl1jty68gcwzA2", "userId2"),
                    Friend("I54fxL5jjITOwjRl1jty68gcwzA2", "userId3")
                )
                friendProvider.get()?.get()?.insertFriends(friends)

                // Insert Workouts
                val workouts = listOf(
                    Workout("workout1", "I54fxL5jjITOwjRl1jty68gcwzA2", "Morning Yoga", "25 min", "https://example.com/workout1.jpg"),
                    Workout("workout2", "I54fxL5jjITOwjRl1jty68gcwzA2", "Leg Day", "40 min", "https://example.com/workout2.jpg"),
                    Workout("workout3", "userId2", "Full Body Workout", "30 min", "https://example.com/workout3.jpg"),
                    Workout("workout4", "userId3", "Cardio Blast", "20 min", "https://example.com/workout4.jpg")
                )
                workoutProvider.get()?.get()?.insertWorkouts(workouts)
                populatedDatabaseProvider.get()?.get()?.flagDatabaseAsPopulated()
            }
        }
}