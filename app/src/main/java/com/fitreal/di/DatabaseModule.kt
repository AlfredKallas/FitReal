package com.fitreal.di

import android.content.Context
import com.fitreal.data.account.AccountRepository
import com.fitreal.data.account.AccountRepositoryImpl
import com.fitreal.data.database.DatabaseCallbackInitializer
import com.fitreal.data.database.FitRealDatabase
import com.fitreal.data.database.FriendDao
import com.fitreal.data.database.PopulatedDatabase
import com.fitreal.data.database.PopulatedDatabaseDao
import com.fitreal.data.database.UserDao
import com.fitreal.data.database.WorkoutDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
        userProvider: Provider<UserDao>,
        workoutProvider: Provider<WorkoutDao>,
        friendProvider: Provider<FriendDao>,
        populatedDatabaseProvider: Provider<PopulatedDatabaseDao>
    ): FitRealDatabase {
        return FitRealDatabase.getDatabase(
            context = context,
            callback = DatabaseCallbackInitializer(
                userProvider = userProvider,
                workoutProvider = workoutProvider,
                friendProvider = friendProvider,
                populatedDatabaseProvider = populatedDatabaseProvider
            )
        )
    }

    @Singleton
    @Provides
    fun provideUserDao(db: FitRealDatabase) = db.userDao()

    @Singleton
    @Provides
    fun provideFriendDao(db: FitRealDatabase) = db.friendDao()

    @Singleton
    @Provides
    fun provideWorkoutDao(db: FitRealDatabase) = db.workoutDao()

    @Singleton
    @Provides
    fun providePopulatedDatabaseDaoDao(db: FitRealDatabase) = db.populatedDatabaseDao()
}
