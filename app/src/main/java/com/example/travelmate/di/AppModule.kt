package com.example.travelmate.di

import com.example.travelmate.data.mapper.Mapper
import com.example.travelmate.data.repository.TravelRepositoryImpl
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
        remoteDataSource: TravelRemoteDataSource,
        mapper: Mapper,
    ): TravelRepository {
        return TravelRepositoryImpl(
            travelRemoteDataSource = remoteDataSource,
            mapper = mapper
        )
    }

    @Provides
    @Singleton
    fun provideTravelRemoteDataSource(travelService: TravelService): TravelRemoteDataSource {
        return TravelRemoteDataSourceImpl(travelService)
    }
}