package com.example.travelmate.di

import com.example.travelmate.data.mapper.Mapper
import com.example.travelmate.data.repository.TravelRepositoryImpl
import com.example.travelmate.data.source.local.dao.TravelDao
import com.example.travelmate.data.source.local.datasource.TravelLocalDataSource
import com.example.travelmate.data.source.local.datasource.TravelLocalDataSourceImpl
import com.example.travelmate.data.source.local.preference.PreferenceDataStore
import com.example.travelmate.data.source.remote.api.TravelService
import com.example.travelmate.data.source.remote.datasource.TravelRemoteDataSource
import com.example.travelmate.data.source.remote.datasource.TravelRemoteDataSourceImpl
import com.example.travelmate.domain.repositories.TravelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideUserRepository(
        localDataSource: TravelLocalDataSource,
        remoteDataSource: TravelRemoteDataSource,
        mapper: Mapper,
    ): TravelRepository {
        return TravelRepositoryImpl(
            travelLocalDataStore = localDataSource,
            travelRemoteDataSource = remoteDataSource,
            mapper = mapper
        )
    }

    @Provides
    @Singleton
    fun provideTravelRemoteDataSource(travelService: TravelService): TravelRemoteDataSource {
        return TravelRemoteDataSourceImpl(travelService)
    }

    @Provides
    @Singleton
    fun provideTravelLocalDataSource(
        travelLocal: PreferenceDataStore,
        travelDao: TravelDao,
    ): TravelLocalDataSource {
        return TravelLocalDataSourceImpl(travelLocal, travelDao)
    }
}