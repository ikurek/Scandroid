package com.ikurek.scandroid.features.createscan.mapper

import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import com.ikurek.scandroid.features.createscan.data.model.ScannedDocuments
import java.time.ZonedDateTime

internal fun GmsDocumentScanningResult.toScannedDocuments(
    createdAt: ZonedDateTime
): ScannedDocuments = ScannedDocuments(
    createdAt = createdAt,
    pdfUri = pdf?.uri,
    imageUris = pages?.map { page -> page.imageUri }.orEmpty()
)
