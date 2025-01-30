package com.example.travelmate.di


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.travelmate.data.source.local.pref.PreferenceDataStore
import com.example.travelmate.data.source.local.pref.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun providePreferenceDataStore(dataStore: DataStore<Preferences>): PreferenceDataStore {
        return PreferenceDataStore(dataStore)
    }
}