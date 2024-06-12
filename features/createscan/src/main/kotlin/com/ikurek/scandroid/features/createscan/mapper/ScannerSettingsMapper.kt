package com.ikurek.scandroid.features.createscan.mapper

import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.ikurek.scandroid.features.settings.data.model.ScannerFormats
import com.ikurek.scandroid.features.settings.data.model.ScannerMode
import com.ikurek.scandroid.features.settings.data.model.ScannerSettings

internal fun ScannerSettings.toGmsDocumentScannerOptions() = GmsDocumentScannerOptions.Builder()
    .withScannerMode(scannerMode)
    .withFormats(scannerFormats)
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
    scannerFormats: ScannerFormats
): GmsDocumentScannerOptions.Builder {
    when (scannerFormats) {
        ScannerFormats.JpegAndPdf ->
            setResultFormats(
                GmsDocumentScannerOptions.RESULT_FORMAT_JPEG,
                GmsDocumentScannerOptions.RESULT_FORMAT_PDF
            )

        ScannerFormats.PdfOnly ->
            setResultFormats(GmsDocumentScannerOptions.RESULT_FORMAT_PDF)

        ScannerFormats.JpegOnly ->
            setResultFormats(GmsDocumentScannerOptions.RESULT_FORMAT_JPEG)
    }

    return this
}
