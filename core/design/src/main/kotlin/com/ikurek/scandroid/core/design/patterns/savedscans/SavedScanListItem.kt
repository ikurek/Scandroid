package com.ikurek.scandroid.core.design.patterns.savedscans

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import java.util.UUID

@Immutable
data class SavedScanListItem(
    @Stable val id: UUID,
    val name: String,
    val createdAt: String,
    val files: SavedScanListItemFiles
) {
    @Immutable
    sealed interface SavedScanListItemFiles {
        data class ImagesOnly(val imageCount: Int) : SavedScanListItemFiles
        data object PdfOnly : SavedScanListItemFiles
        data class PdfAndImages(val imageCount: Int) : SavedScanListItemFiles
    }
}
