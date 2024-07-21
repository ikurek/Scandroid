package com.ikurek.scandroid.features.scandetails.usecase

import android.util.Log
import com.ikurek.scandroid.analytics.ErrorTracker
import com.ikurek.scandroid.core.platform.Platform
import com.ikurek.scandroid.features.savedscans.data.model.SavedScanFiles
import javax.inject.Inject

class OpenScanFilesOutside @Inject internal constructor(
    private val platform: Platform,
    private val errorTracker: ErrorTracker
) {
    suspend operator fun invoke(
        savedScanFiles: SavedScanFiles,
        selectedFileType: SelectedFileType
    ): Result<Unit> = when (savedScanFiles) {
        is SavedScanFiles.ImagesOnly -> platform.openImageFilesOutside(savedScanFiles.imageFiles)
        is SavedScanFiles.PdfOnly -> platform.openPdfFileOutside(savedScanFiles.pdfFile)
        is SavedScanFiles.PdfAndImages -> when (selectedFileType) {
            SelectedFileType.Images -> platform.openImageFilesOutside(savedScanFiles.imageFiles)
            SelectedFileType.PDF -> platform.openPdfFileOutside(savedScanFiles.pdfFile)
            null -> error("Unable to determine which scan file should be opened")
        }.onFailure { exception ->
            Log.d(this::class.simpleName, "Failed to open scan file: $exception")
            errorTracker.trackNonFatal(exception, "Failed to open scan file")
        }
    }
}
