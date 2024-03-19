package com.ikurek.scandroid.core.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ikurek.scandroid.core.database.entity.ScanEntity
import com.ikurek.scandroid.core.database.room.converters.UuidConverter
import com.ikurek.scandroid.core.database.room.converters.ZonedDateTimeConverter
import com.ikurek.scandroid.core.database.room.dao.ScanDao

@Database(entities = [ScanEntity::class], version = 1, exportSchema = false)
@TypeConverters(UuidConverter::class, ZonedDateTimeConverter::class)
internal abstract class RoomAppDatabase : RoomDatabase() {
    abstract fun scanDao(): ScanDao
}
