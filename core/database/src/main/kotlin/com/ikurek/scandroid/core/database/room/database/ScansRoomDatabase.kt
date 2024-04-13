package com.ikurek.scandroid.core.database.room.database

import com.ikurek.scandroid.core.database.ScansDatabase
import com.ikurek.scandroid.core.database.entity.ScanEntity
import com.ikurek.scandroid.core.database.room.dao.ScanDao
import java.util.UUID
import javax.inject.Inject

internal class ScansRoomDatabase @Inject constructor(
    private val scanDao: ScanDao
) : ScansDatabase {

    override suspend fun findAll(): List<ScanEntity> = scanDao.findAll()

    override suspend fun findById(id: UUID): ScanEntity? = scanDao.findById(id.toString())

    override suspend fun save(scanEntity: ScanEntity) = scanDao.save(scanEntity)
}
