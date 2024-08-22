package com.ikurek.scandroid.features.savedscans.usecase

import com.ikurek.scandroid.features.savedscans.data.model.ExtendedScanInfo
import com.ikurek.scandroid.features.savedscans.data.model.SavedScan
import javax.inject.Inject

class GetExtendedInfoFromSavedScan @Inject internal constructor() {

    operator fun invoke(savedScan: SavedScan) = ExtendedScanInfo(
        id = savedScan.id,
        createdAt = savedScan.createdAt,
        updatedAt = savedScan.updatedAt,
        lastAccessedAt = savedScan.lastAccessedAt,
        imageFilesCount = savedScan.files.imageFiles.orEmpty().count(),
        imageFilesSizeInBytes = savedScan.files.imageFiles.orEmpty().sumOf { it.length() }.toInt(),
        documentFilesCount = savedScan.files.pdfFile?.let { 1 } ?: 0,
        documentFilesSizeInBytes = (savedScan.files.pdfFile?.length() ?: 0).toInt()
    )
}
