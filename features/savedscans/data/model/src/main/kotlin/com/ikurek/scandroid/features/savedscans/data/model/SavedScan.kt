package com.ikurek.scandroid.features.savedscans.data.model

import java.util.UUID

data class SavedScan(
    val id: UUID,
    val name: String,
    val description: String?,
    val files: SavedScanFiles
)
