package com.ikurek.scandroid.features.scandetails.ui.scandetails.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.core.design.components.dialogs.InfoDialog
import com.ikurek.scandroid.features.savedscans.data.model.ExtendedScanInfo
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.UUID
import com.ikurek.scandroid.core.translations.R as TranslationsR

@Composable
internal fun ExtendedScanInfoDialog(
    extendedScanInfo: ExtendedScanInfo,
    onDismiss: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val dateTimeFormatter = remember {
        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.LONG)
    }

    InfoDialog(
        title = stringResource(TranslationsR.string.scan_details_extended_info_title),
        content = {
            Content(
                extendedScanInfo = extendedScanInfo,
                dateTimeFormatter = dateTimeFormatter,
                onCopyValueClick = clipboardManager::setText
            )
        },
        onDismissRequest = onDismiss
    )
}

@Suppress("LongMethod")
@Composable
private fun Content(
    extendedScanInfo: ExtendedScanInfo,
    dateTimeFormatter: DateTimeFormatter,
    onCopyValueClick: ((value: AnnotatedString) -> Unit)
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ScanAttributeRow(
            name = stringResource(TranslationsR.string.scan_details_extended_info_attribute_id),
            value = extendedScanInfo.id.toString(),
            onCopyValueClick = onCopyValueClick
        )

        HorizontalDivider()

        ScanAttributeRow(
            name = stringResource(TranslationsR.string.scan_details_extended_info_attribute_created_at),
            value = extendedScanInfo.createdAt.format(dateTimeFormatter),
            onCopyValueClick = onCopyValueClick
        )

        HorizontalDivider()

        ScanAttributeRow(
            name = stringResource(TranslationsR.string.scan_details_extended_info_attribute_updated_at),
            value = extendedScanInfo.updatedAt.format(dateTimeFormatter),
            onCopyValueClick = onCopyValueClick
        )

        HorizontalDivider()

        ScanAttributeRow(
            name = stringResource(TranslationsR.string.scan_details_extended_info_attribute_last_accessed_at),
            value = extendedScanInfo.lastAccessedAt.format(dateTimeFormatter),
            onCopyValueClick = onCopyValueClick
        )

        HorizontalDivider()

        ScanAttributeRow(
            name = stringResource(TranslationsR.string.scan_details_extended_info_attribute_images_count),
            value = pluralStringResource(
                id = TranslationsR.plurals.common_file,
                count = extendedScanInfo.imageFilesCount,
                extendedScanInfo.imageFilesCount
            ),
        )

        HorizontalDivider()

        ScanAttributeRow(
            name = stringResource(TranslationsR.string.scan_details_extended_info_attribute_images_size),
            value = pluralStringResource(
                id = TranslationsR.plurals.common_unit_bytes,
                count = extendedScanInfo.imageFilesSizeInBytes,
                extendedScanInfo.imageFilesSizeInBytes
            ),
        )

        HorizontalDivider()

        ScanAttributeRow(
            name = stringResource(TranslationsR.string.scan_details_extended_info_attribute_documents_count),
            value = pluralStringResource(
                id = TranslationsR.plurals.common_file,
                count = extendedScanInfo.documentFilesCount,
                extendedScanInfo.documentFilesCount
            ),
        )

        HorizontalDivider()

        ScanAttributeRow(
            name = stringResource(TranslationsR.string.scan_details_extended_info_attribute_documents_size),
            value = pluralStringResource(
                id = TranslationsR.plurals.common_unit_bytes,
                count = extendedScanInfo.documentFilesSizeInBytes,
                extendedScanInfo.documentFilesSizeInBytes
            ),
        )
    }
}

@Composable
private fun ScanAttributeRow(
    name: String,
    value: String,
    onCopyValueClick: ((value: AnnotatedString) -> Unit)? = null,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                color = AlertDialogDefaults.textContentColor

            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                color = AlertDialogDefaults.textContentColor
            )
        }

        onCopyValueClick?.let {
            Icon(
                imageVector = Icons.Outlined.ContentCopy,
                contentDescription = stringResource(TranslationsR.string.common_copy_button_icon),
                modifier = Modifier
                    .size(16.dp)
                    .clickable { onCopyValueClick(AnnotatedString(value)) },
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    ScandroidTheme {
        ExtendedScanInfoDialog(
            extendedScanInfo = ExtendedScanInfo(
                id = UUID.randomUUID(),
                createdAt = ZonedDateTime.of(2024, 10, 13, 11, 23, 45, 0, ZoneId.of("UTC")),
                updatedAt = ZonedDateTime.of(2024, 10, 13, 11, 23, 45, 0, ZoneId.of("UTC")),
                lastAccessedAt = ZonedDateTime.of(2024, 10, 13, 11, 23, 45, 0, ZoneId.of("UTC")),
                imageFilesCount = 1,
                imageFilesSizeInBytes = 1,
                documentFilesCount = 1,
                documentFilesSizeInBytes = 12345
            ),
            onDismiss = {}
        )
    }
}
