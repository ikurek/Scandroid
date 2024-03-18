package com.ikurek.scandroid.features.createscan.model

import android.net.Uri

data class ScannedDocuments(
    val pdfUri: Uri?,
    val imageUris: List<Uri>
) {
    val hasMultipleDocumentFormats: Boolean = pdfUri != null && imageUris.isNotEmpty()
}
