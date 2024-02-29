package com.ikurek.scandroid.features.createscan.mapper

import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import com.ikurek.scandroid.features.createscan.model.ScannedDocuments

internal fun GmsDocumentScanningResult.toScannedDocuments(): ScannedDocuments = ScannedDocuments(
    pdfUri = pdf?.uri,
    imageUris = pages?.map { page -> page.imageUri }.orEmpty()
)
