package com.ikurek.scandroid.features.savedscans.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.features.savedscans.data.model.SavedScan
import com.ikurek.scandroid.features.savedscans.data.model.SavedScanFiles
import java.io.File
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.UUID
import com.ikurek.scandroid.core.translations.R as TranslationsR

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SavedScanList(
    scans: List<SavedScan>,
    onScanClick: (UUID) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = scans,
            key = { scan -> scan.id }
        ) { scan ->
            SavedScanCard(
                scan = scan,
                onClick = { onScanClick(scan.id) },
                modifier = Modifier.animateItemPlacement()
            )
        }
    }
}

@Composable
private fun SavedScanCard(
    scan: SavedScan,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            ScanImage(scan.files)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                ScanTitle(text = scan.name)
                ScanDate(date = scan.createdAt)
                Spacer(modifier = Modifier.height(4.dp))
                ScanFilesDescription(files = scan.files)
            }
        }
    }
}

@Composable
private fun ScanImage(
    files: SavedScanFiles,
    modifier: Modifier = Modifier
) {
    Icon(
        modifier = modifier.size(32.dp),
        imageVector = when (files) {
            is SavedScanFiles.ImagesOnly -> Icons.Default.Collections
            is SavedScanFiles.PdfAndImages -> Icons.Default.PictureAsPdf
            is SavedScanFiles.PdfOnly -> Icons.AutoMirrored.Default.InsertDriveFile
        },
        contentDescription = null
    )
}

@Composable
private fun ScanTitle(text: String) {
    Text(text = text, style = MaterialTheme.typography.titleMedium)
}

@Composable
private fun ScanDate(date: ZonedDateTime) {
    Text(
        text = date.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
        style = MaterialTheme.typography.labelSmall
    )
}

@Composable
private fun ScanFilesDescription(files: SavedScanFiles) {
    val text = when (files) {
        is SavedScanFiles.ImagesOnly -> pluralStringResource(
            id = TranslationsR.plurals.saved_scans_scan_files_only_images,
            count = files.imageFiles.size,
            files.imageFiles.size
        )

        is SavedScanFiles.PdfAndImages -> pluralStringResource(
            id = TranslationsR.plurals.saved_scans_scan_files_pdf_and_images,
            count = files.imageFiles.size,
            files.imageFiles.size
        )

        is SavedScanFiles.PdfOnly -> stringResource(
            id = TranslationsR.string.saved_scans_scan_files_only_pdf
        )
    }

    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall
    )
}

@PreviewLightDark
@Composable
private fun Preview() {
    ScandroidTheme {
        SavedScanList(
            scans = listOf(
                SavedScan(
                    id = UUID.randomUUID(),
                    name = "PDF Scan",
                    description = "Scan description for PDF scan",
                    createdAt = ZonedDateTime.of(2024, 10, 13, 11, 23, 45, 0, ZoneId.of("UTC")),
                    updatedAt = ZonedDateTime.of(2024, 10, 13, 11, 23, 45, 0, ZoneId.of("UTC")),
                    lastAccessedAt = ZonedDateTime.of(
                        2024,
                        10,
                        13,
                        11,
                        23,
                        45,
                        0,
                        ZoneId.of("UTC")
                    ),
                    files = SavedScanFiles.PdfOnly(pdfFile = File("path"))
                ),
                SavedScan(
                    id = UUID.randomUUID(),
                    name = "Image Scan",
                    description = "Scan description for image scan",
                    createdAt = ZonedDateTime.of(2024, 10, 13, 11, 23, 45, 0, ZoneId.of("UTC")),
                    updatedAt = ZonedDateTime.of(2024, 10, 13, 11, 23, 45, 0, ZoneId.of("UTC")),
                    lastAccessedAt = ZonedDateTime.of(
                        2024,
                        10,
                        13,
                        11,
                        23,
                        45,
                        0,
                        ZoneId.of("UTC")
                    ),
                    files = SavedScanFiles.ImagesOnly(imageFiles = listOf(File("path")))
                ),
                SavedScan(
                    id = UUID.randomUUID(),
                    name = "Image & PDF Scan",
                    description = "Scan description for image & PDF scan",
                    createdAt = ZonedDateTime.of(2024, 10, 13, 11, 23, 45, 0, ZoneId.of("UTC")),
                    updatedAt = ZonedDateTime.of(2024, 10, 13, 11, 23, 45, 0, ZoneId.of("UTC")),
                    lastAccessedAt = ZonedDateTime.of(
                        2024,
                        10,
                        13,
                        11,
                        23,
                        45,
                        0,
                        ZoneId.of("UTC")
                    ),
                    files = SavedScanFiles.PdfAndImages(
                        pdfFile = File("path"),
                        imageFiles = listOf(File("path"), File("path"))
                    )
                )
            ),
            onScanClick = { }
        )
    }
}
