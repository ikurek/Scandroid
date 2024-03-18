package com.ikurek.scandroid.features.createscan.usecase

import com.ikurek.scandroid.features.createscan.model.ScannedDocuments
import com.ikurek.scandroid.features.createscan.model.ScannerFileFormat
import kotlinx.coroutines.delay
import javax.inject.Inject

class SaveScannedDocuments @Inject internal constructor() {

    @Suppress("MagicNumber")
    suspend operator fun invoke(
        name: String,
        description: String,
        scannedDocuments: ScannedDocuments,
        selectedFileFormats: Set<ScannerFileFormat>
    ) = runCatching {
        assert(scannedDocuments.pdfUri != null || scannedDocuments.imageUris.isNotEmpty()) {
            "Either PDF or JPEG file URIs are required"
        }
        assert(selectedFileFormats.isNotEmpty()) {
            "At least one file format has to be selected"
        }
        // TODO: Handle
        delay(5000)
    }
}
