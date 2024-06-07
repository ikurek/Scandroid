package com.ikurek.scandroid.features.savedscans.data.repository.mapper

import com.ikurek.scandroid.core.database.entity.ScanEntity
import com.ikurek.scandroid.features.savedscans.data.model.SavedScan
import com.ikurek.scandroid.features.savedscans.data.model.SavedScanFiles

internal fun ScanEntity.toSavedScan(files: SavedScanFiles) = SavedScan(
    id = id,
    name = name,
    description = if (description.isNullOrBlank()) null else description,
    createdAt = createdAt,
    updatedAt = updatedAt,
    lastAccessedAt = lastAccessedAt,
    files = files
)
