package com.example.data.di

import com.example.data.dataSource.onlineDataSource.OnlineQuranRepositoryImpl
import com.example.domain.dataSource.onlineDataSource.OnlineQuranRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object OnlineDataSourceModule {

    @Provides
    fun provideOnlineQuranRepository(impl: OnlineQuranRepositoryImpl): OnlineQuranRepository {

        return impl
    }
}