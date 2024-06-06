package com.ikurek.scandroid.features.createscan.data.repository.mapper

import com.ikurek.scandroid.core.database.entity.ScanEntity
import com.ikurek.scandroid.features.createscan.data.model.NewScan

internal fun NewScan.toEntity() = ScanEntity(
    id = id,
    name = name,
    description = description,
    createdAt = createdAt,
    updatedAt = createdAt,
    lastAccessedAt = createdAt
)
