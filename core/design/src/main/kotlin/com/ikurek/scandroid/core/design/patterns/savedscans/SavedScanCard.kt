package com.ikurek.scandroid.core.design.patterns.savedscans

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.ikurek.scandroid.core.design.patterns.savedscans.SavedScanListItem.SavedScanListItemFiles
import com.ikurek.scandroid.core.translations.R
import java.util.UUID

@Composable
fun SavedScanCard(
    scan: SavedScanListItem,
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
            ScanImage(files = scan.files)
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
private fun ScanImage(files: SavedScanListItemFiles) {
    Icon(
        modifier = Modifier.size(32.dp),
        imageVector = when (files) {
            is SavedScanListItemFiles.ImagesOnly -> Icons.Default.Collections
            is SavedScanListItemFiles.PdfAndImages -> Icons.Default.PictureAsPdf
            is SavedScanListItemFiles.PdfOnly -> Icons.AutoMirrored.Default.InsertDriveFile
        },
        contentDescription = null
    )
}

@Composable
private fun ScanTitle(text: String) {
    Text(text = text, style = MaterialTheme.typography.titleMedium)
}

@Composable
private fun ScanDate(date: String) {
    Text(
        text = date,
        style = MaterialTheme.typography.labelSmall
    )
}

@Composable
private fun ScanFilesDescription(files: SavedScanListItemFiles) {
    val text = when (files) {
        is SavedScanListItemFiles.ImagesOnly -> pluralStringResource(
            id = R.plurals.saved_scans_scan_files_only_images,
            count = files.imageCount,
            files.imageCount
        )

        is SavedScanListItemFiles.PdfAndImages -> pluralStringResource(
            id = R.plurals.saved_scans_scan_files_pdf_and_images,
            count = files.imageCount,
            files.imageCount
        )

        is SavedScanListItemFiles.PdfOnly -> stringResource(
            id = R.string.saved_scans_scan_files_only_pdf
        )
    }

    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall
    )
}

@PreviewLightDark
@Composable
private fun PreviewImageScan() {
    ScandroidTheme {
        SavedScanCard(
            scan = SavedScanListItem(
                id = UUID.randomUUID(),
                name = "Image Scan",
                createdAt = "Jun 7, 2024, 2:04:07 AM",
                files = SavedScanListItemFiles.ImagesOnly(1)
            ),
            onClick = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewPdfScan() {
    ScandroidTheme {
        SavedScanCard(
            scan = SavedScanListItem(
                id = UUID.randomUUID(),
                name = "PDF Scan",
                createdAt = "Jun 7, 2024, 2:04:07 AM",
                files = SavedScanListItemFiles.PdfOnly
            ),
            onClick = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewImageAndPdfScan() {
    ScandroidTheme {
        SavedScanCard(
            scan = SavedScanListItem(
                id = UUID.randomUUID(),
                name = "Image & PDF Scan",
                createdAt = "Jun 7, 2024, 2:04:07 AM",
                files = SavedScanListItemFiles.PdfAndImages(3)
            ),
            onClick = {}
        )
    }
}
