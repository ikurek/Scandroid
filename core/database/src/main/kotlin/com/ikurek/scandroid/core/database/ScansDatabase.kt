package com.ikurek.scandroid.core.database

import com.ikurek.scandroid.core.database.entity.ScanEntity

interface ScansDatabase {

    suspend fun findAll(): List<ScanEntity>

    suspend fun save(scanEntity: ScanEntity)
}
