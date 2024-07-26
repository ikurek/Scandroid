package com.ikurek.scandroid.features.savedscans.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.core.design.patterns.savedscans.SavedScanCard
import com.ikurek.scandroid.core.design.patterns.savedscans.SavedScanListItem
import com.ikurek.scandroid.core.design.patterns.savedscans.SavedScanListItem.SavedScanListItemFiles
import java.util.UUID

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
