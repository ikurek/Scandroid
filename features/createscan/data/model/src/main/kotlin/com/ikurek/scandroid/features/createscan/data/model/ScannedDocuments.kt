package com.ikurek.scandroid.features.createscan.data.model

import android.net.Uri
import java.time.ZonedDateTime

data class ScannedDocuments(
    val createdAt: ZonedDateTime,
    val pdfUri: Uri?,
    val imageUris: List<Uri>
) {
    val hasMultipleDocumentFormats: Boolean = pdfUri != null && imageUris.isNotEmpty()

    val isEmpty: Boolean = pdfUri == null && imageUris.isEmpty()
}
