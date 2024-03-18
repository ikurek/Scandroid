package com.ikurek.scandroid.features.createscan.ui.newscan.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DesignServices
import androidx.compose.material.icons.filled.PermMedia
import androidx.compose.material.icons.filled.Tag
import androidx.compose.ui.graphics.vector.ImageVector
import com.ikurek.scandroid.core.translations.R as TranslationsR

internal enum class Section(
    @StringRes val sectionNameRes: Int,
    val sectionIcon: ImageVector
) {
    Info(
        sectionNameRes = TranslationsR.string.new_scan_document_info_section,
        sectionIcon = Icons.Default.DesignServices
    ),
    Formats(
        sectionNameRes = TranslationsR.string.new_scan_document_formats_section,
        sectionIcon = Icons.Default.PermMedia
    ),
    Tags(
        sectionNameRes = TranslationsR.string.new_scan_document_tags_section,
        sectionIcon = Icons.Default.Tag
    )
}
