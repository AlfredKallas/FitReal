package com.fitreal.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, Workout::class, Friend::class, PopulatedDatabase::class], version = 1)
abstract class FitRealDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun friendDao(): FriendDao
    abstract fun populatedDatabaseDao(): PopulatedDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: FitRealDatabase? = null

        private const val DATABASE_NAME = "FitReal_Database"

        fun getDatabase(context: Context, callback: Callback): FitRealDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FitRealDatabase::class.java,
                    DATABASE_NAME
                ).addCallback(
                    callback
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}