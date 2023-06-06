package com.rundgrun.stairs.di

import com.rundgrun.stairs.data.ImplStairsConfigRepository
import com.rundgrun.stairs.domain.repository.StairsConfigRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StairsConfigModule {

    @Binds
    @Singleton
    abstract fun getStairsConfigRepository(stairsConfigRepository: ImplStairsConfigRepository): StairsConfigRepository
}