package com.ikurek.scandroid.features.scandetails.ui.scandetails.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material.icons.filled.Image
import androidx.compose.ui.graphics.vector.ImageVector
import com.ikurek.scandroid.core.translations.R as TranslationsR

enum class PdfAndImagesTabs(
    @StringRes val textRes: Int,
    val icon: ImageVector
) {
    Images(
        textRes = TranslationsR.string.scan_details_tab_images,
        icon = Icons.Default.Image
    ),
    PDF(
        textRes = TranslationsR.string.scan_details_tab_document,
        icon = Icons.AutoMirrored.Default.InsertDriveFile
    )
}
