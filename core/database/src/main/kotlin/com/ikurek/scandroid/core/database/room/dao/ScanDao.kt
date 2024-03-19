package com.ikurek.scandroid.core.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ikurek.scandroid.core.database.entity.ScanEntity

@Dao
internal interface ScanDao {
    @Query("SELECT * FROM scans")
    suspend fun findAll(): List<ScanEntity>

    @Insert
    suspend fun save(scanEntity: ScanEntity)
}
