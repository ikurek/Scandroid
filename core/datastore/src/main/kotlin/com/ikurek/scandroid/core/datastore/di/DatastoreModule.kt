package com.ikurek.scandroid.core.datastore.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.ikurek.scandroid.core.datastore.PreferencesStorage
import com.ikurek.scandroid.core.datastore.Storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SettingsStorage

@Module
@InstallIn(SingletonComponent::class)
internal object DatastoreModule {

    @SettingsStorage
    @Provides
    @Singleton
    fun providesSettingsStorage(
        @ApplicationContext context: Context
    ): Storage = PreferencesStorage(
        dataStore = PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile("${context.packageName}_settings")
        }
    )
}
