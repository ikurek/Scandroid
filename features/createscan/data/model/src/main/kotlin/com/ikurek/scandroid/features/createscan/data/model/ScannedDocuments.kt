package com.ikurek.scandroid.features.createscan.data.model

import android.net.Uri

data class ScannedDocuments(
    val pdfUri: Uri?,
    val imageUris: List<Uri>
) {
    val hasMultipleDocumentFormats: Boolean = pdfUri != null && imageUris.isNotEmpty()

    val isEmpty: Boolean = pdfUri == null && imageUris.isEmpty()
}
