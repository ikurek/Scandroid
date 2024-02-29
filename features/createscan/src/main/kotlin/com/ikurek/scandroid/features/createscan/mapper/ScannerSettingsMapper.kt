package com.ikurek.scandroid.features.createscan.mapper

import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.ikurek.scandroid.features.createscan.model.ScannerFormat
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
    formats: List<ScannerFormat>
): GmsDocumentScannerOptions.Builder {
    when {
        formats.contains(ScannerFormat.JPEG) && formats.contains(ScannerFormat.PDF) ->
            setResultFormats(
                GmsDocumentScannerOptions.RESULT_FORMAT_JPEG,
                GmsDocumentScannerOptions.RESULT_FORMAT_PDF
            )

        formats.contains(ScannerFormat.PDF) ->
            setResultFormats(GmsDocumentScannerOptions.RESULT_FORMAT_PDF)

        formats.contains(ScannerFormat.JPEG) ->
            setResultFormats(GmsDocumentScannerOptions.RESULT_FORMAT_JPEG)
    }

    return this
}
