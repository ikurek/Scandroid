package com.ikurek.scandroid.features.createscan.model

import android.net.Uri

data class ScannedDocuments(
    private val pdfUri: Uri?,
    private val imageUris: List<Uri>
)
