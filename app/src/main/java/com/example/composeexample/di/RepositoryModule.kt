package com.example.composeexample.di

import com.example.composeexample.repository.CoinRepository
import com.example.composeexample.repository.CoinRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun coinRepository(repo: CoinRepositoryImpl): CoinRepository
}

