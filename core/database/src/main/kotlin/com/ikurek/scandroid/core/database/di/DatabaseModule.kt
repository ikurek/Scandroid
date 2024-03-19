package com.ikurek.scandroid.core.database.di

import com.ikurek.scandroid.core.database.ScansDatabase
import com.ikurek.scandroid.core.database.room.dao.ScanDao
import com.ikurek.scandroid.core.database.room.database.ScansRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    fun provideScansDatabase(scanDao: ScanDao): ScansDatabase = ScansRoomDatabase(scanDao)
}
