package com.ikurek.scandroid.features.savedscans.data.model

import java.time.ZonedDateTime
import java.util.UUID

data class ExtendedScanInfo(
    val id: UUID,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
    val lastAccessedAt: ZonedDateTime,
    val imageFilesCount: Int,
    val imageFilesSizeInBytes: Int,
    val documentFilesCount: Int,
    val documentFilesSizeInBytes: Int
)
