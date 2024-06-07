package com.ikurek.scandroid.features.savedscans.data.model

import java.time.ZonedDateTime
import java.util.UUID

data class SavedScan(
    val id: UUID,
    val name: String,
    val description: String?,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
    val lastAccessedAt: ZonedDateTime,
    val files: SavedScanFiles
)
