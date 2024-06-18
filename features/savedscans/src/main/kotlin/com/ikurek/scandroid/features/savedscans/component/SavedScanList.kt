package com.ikurek.scandroid.features.savedscans.component

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
import com.ikurek.scandroid.features.savedscans.model.SavedScanListItem
import com.ikurek.scandroid.features.savedscans.model.SavedScanListItem.SavedScanListItemFiles
import java.util.UUID
import com.ikurek.scandroid.core.translations.R as TranslationsR

@Composable
internal fun SavedScanList(
    items: List<SavedScanListItem>,
    onScanClick: (UUID) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = items,
            key = { scan -> scan.id }
        ) { scan ->
            SavedScanCard(
                scan = scan,
                onClick = { onScanClick(scan.id) }
            )
        }
    }
}

@Composable
private fun SavedScanCard(
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
            id = TranslationsR.plurals.saved_scans_scan_files_only_images,
            count = files.imageCount,
            files.imageCount
        )

        is SavedScanListItemFiles.PdfAndImages -> pluralStringResource(
            id = TranslationsR.plurals.saved_scans_scan_files_pdf_and_images,
            count = files.imageCount,
            files.imageCount
        )

        is SavedScanListItemFiles.PdfOnly -> stringResource(
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
            items = listOf(
                SavedScanListItem(
                    id = UUID.randomUUID(),
                    name = "PDF Scan",
                    createdAt = "Jun 7, 2024, 2:04:07 AM",
                    files = SavedScanListItemFiles.PdfOnly
                ),
                SavedScanListItem(
                    id = UUID.randomUUID(),
                    name = "Image Scan",
                    createdAt = "Jun 7, 2024, 2:04:07 AM",
                    files = SavedScanListItemFiles.ImagesOnly(1)
                ),
                SavedScanListItem(
                    id = UUID.randomUUID(),
                    name = "Image & PDF Scan",
                    createdAt = "Jun 7, 2024, 2:04:07 AM",
                    files = SavedScanListItemFiles.PdfAndImages(3)
                )
            ),
            onScanClick = { }
        )
    }
}
