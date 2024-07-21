package com.ikurek.scandroid.core.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ikurek.scandroid.core.database.entity.ScanEntity
import java.time.ZonedDateTime
import java.util.UUID

@Dao
internal interface ScanDao {
    @Query("SELECT * FROM scans")
    suspend fun findAll(): List<ScanEntity>

    @Query("SELECT * FROM scans WHERE id = :id")
    suspend fun findById(id: UUID): ScanEntity?

    @Insert
    suspend fun save(scanEntity: ScanEntity)

    @Query("UPDATE scans SET last_accessed_at=:lastAccessedAt WHERE id = :id")
    suspend fun updateLastAccessedAt(id: UUID, lastAccessedAt: ZonedDateTime)

    @Query("DELETE FROM scans WHERE id = :id")
    suspend fun deleteById(id: UUID)
}
