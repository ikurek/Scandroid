package com.ikurek.scandroid.features.createscan.mapper

import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.ikurek.scandroid.features.createscan.model.ScannerFileFormat
import com.ikurek.scandroid.features.createscan.model.ScannerMode
import com.ikurek.scandroid.features.createscan.model.ScannerSettings

internal fun ScannerSettings.toGmsDocumentScannerOptions() = GmsDocumentScannerOptions.Builder()
    .withScannerMode(scannerMode)
    .withFormats(supportedFormats)
    .build()

private fun GmsDocumentScannerOptions.Builder.withScannerMode(
    scannerMode: ScannerMode
): GmsDocumentScannerOptions.Builder {
    setScannerMode(
        when (scannerMode) {
            ScannerMode.Base -> GmsDocumentScannerOptions.SCANNER_MODE_BASE
            ScannerMode.BaseWithFilter -> GmsDocumentScannerOptions.SCANNER_MODE_BASE_WITH_FILTER
            ScannerMode.Full -> GmsDocumentScannerOptions.SCANNER_MODE_FULL
        }
    )

    return this
}

private fun GmsDocumentScannerOptions.Builder.withFormats(
    formats: List<ScannerFileFormat>
): GmsDocumentScannerOptions.Builder {
    when {
        formats.contains(ScannerFileFormat.JPEG) && formats.contains(ScannerFileFormat.PDF) ->
            setResultFormats(
                GmsDocumentScannerOptions.RESULT_FORMAT_JPEG,
                GmsDocumentScannerOptions.RESULT_FORMAT_PDF
            )

        formats.contains(ScannerFileFormat.PDF) ->
            setResultFormats(GmsDocumentScannerOptions.RESULT_FORMAT_PDF)

        formats.contains(ScannerFileFormat.JPEG) ->
            setResultFormats(GmsDocumentScannerOptions.RESULT_FORMAT_JPEG)
    }

    return this
}
