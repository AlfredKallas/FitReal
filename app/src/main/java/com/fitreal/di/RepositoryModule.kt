package com.fitreal.di

import com.fitreal.data.account.AccountRepository
import com.fitreal.data.account.AccountRepositoryImpl
import com.fitreal.data.workout.WorkoutsRepository
import com.fitreal.data.workout.WorkoutsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

  @Binds abstract fun provideAccountRepository(impl: AccountRepositoryImpl): AccountRepository

  @Binds abstract fun provideWorkoutFeedsRepository(impl: WorkoutsRepositoryImpl): WorkoutsRepository

}
