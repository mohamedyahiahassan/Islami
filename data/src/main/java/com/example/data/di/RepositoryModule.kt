package com.example.data.di

import com.example.data.repository.QuranRepositoryImpl
import com.example.domain.repository.QuranRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideQuranRepository(impl: QuranRepositoryImpl):QuranRepository{

        return impl
    }
}