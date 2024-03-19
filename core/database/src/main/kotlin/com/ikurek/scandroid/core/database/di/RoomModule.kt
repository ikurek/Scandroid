package com.ikurek.scandroid.core.database.di

import android.content.Context
import androidx.room.Room
import com.ikurek.scandroid.core.database.room.RoomAppDatabase
import com.ikurek.scandroid.core.database.room.dao.ScanDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object RoomModule {

    @Provides
    fun provideRoomAppDatabase(
        @ApplicationContext applicationContext: Context
    ): RoomAppDatabase = Room.databaseBuilder(
        context = applicationContext,
        klass = RoomAppDatabase::class.java,
        name = "scandroid-database"
    ).build()

    @Provides
    fun provideScanDao(roomAppDatabase: RoomAppDatabase): ScanDao = roomAppDatabase.scanDao()
}
