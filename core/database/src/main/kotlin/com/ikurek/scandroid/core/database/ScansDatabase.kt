package com.ikurek.scandroid.core.database

import com.ikurek.scandroid.core.database.entity.ScanEntity
import java.time.ZonedDateTime
import java.util.UUID

interface ScansDatabase {

    suspend fun findAll(): List<ScanEntity>

    suspend fun findById(id: UUID): ScanEntity?

    suspend fun save(scanEntity: ScanEntity)

    suspend fun updateLastAccessedAt(id: UUID, lastAccessedAt: ZonedDateTime)
}
