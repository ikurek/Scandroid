package com.ikurek.scandroid.features.scandetails.ui.scandetails.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ikurek.scandroid.common.ui.pdfview.PdfView
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.features.savedscans.data.model.SavedScan
import com.ikurek.scandroid.features.savedscans.data.model.SavedScanFiles
import com.ikurek.scandroid.features.scandetails.ui.scandetails.model.PdfAndImagesTabs
import kotlinx.coroutines.launch
import java.io.File
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.UUID
import com.ikurek.scandroid.core.translations.R as TranslationsR

@Composable
internal fun ScanDetails(
    scan: SavedScan,
    onImageClick: (scanId: UUID, imageIndex: Int) -> Unit,
    onFileTypePageChange: (currentTab: PdfAndImagesTabs) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = TranslationsR.string.scan_details_created_at_label),
            modifier = Modifier.padding(horizontal = 24.dp),
            style = MaterialTheme.typography.labelMedium
        )

        Text(
            text = scan.createdAt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
            modifier = Modifier.padding(horizontal = 24.dp),
            style = MaterialTheme.typography.bodyMedium
        )

        scan.description?.let { description ->
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = TranslationsR.string.scan_details_description_label),
                modifier = Modifier.padding(horizontal = 24.dp),
                style = MaterialTheme.typography.labelMedium
            )

            Text(
                text = description,
                modifier = Modifier.padding(horizontal = 24.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (scan.files) {
            is SavedScanFiles.ImagesOnly -> ImagesOnlyScanDetails(
                images = scan.files.imageFiles!!,
                onImageClick = { index -> onImageClick(scan.id, index) }
            )

            is SavedScanFiles.PdfOnly -> PdfOnlyScanDetails(
                document = scan.files.pdfFile!!
            )

            is SavedScanFiles.PdfAndImages -> PdfAndImagesScanDetails(
                images = scan.files.imageFiles!!,
                document = scan.files.pdfFile!!,
                onImageClick = { index -> onImageClick(scan.id, index) },
                onFileTypePageChange = onFileTypePageChange
            )
        }
    }
}

@Composable
private fun ImagesOnlyScanDetails(
    images: List<File>,
    onImageClick: (index: Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(
            items = images,
            key = { _, image -> image.name }
        ) { index, image ->
            Card(onClick = { onImageClick(index) }) {
                AsyncImage(
                    model = image,
                    contentDescription = stringResource(
                        id = TranslationsR.string.scan_details_scanned_image_content_descriptions,
                        index
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
private fun PdfOnlyScanDetails(
    document: File
) {
    PdfView(
        document,
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun PdfAndImagesScanDetails(
    images: List<File>,
    document: File,
    onImageClick: (index: Int) -> Unit,
    onFileTypePageChange: (currentTab: PdfAndImagesTabs) -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = PdfAndImagesTabs.Images.ordinal,
        pageCount = { PdfAndImagesTabs.entries.size }
    )

    val tabRowCoroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        onFileTypePageChange(PdfAndImagesTabs.entries[pagerState.currentPage])
    }

    PrimaryTabRow(selectedTabIndex = pagerState.currentPage) {
        PdfAndImagesTabs.entries.forEach { tab ->
            Tab(
                selected = tab.ordinal == pagerState.currentPage,
                onClick = {
                    tabRowCoroutineScope.launch {
                        pagerState.animateScrollToPage(tab.ordinal)
                    }
                },
                text = {
                    Text(text = stringResource(id = tab.textRes))
                },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = null
                    )
                }
            )
        }
    }

    HorizontalPager(state = pagerState) { pageIndex ->
        when (PdfAndImagesTabs.entries[pageIndex]) {
            PdfAndImagesTabs.Images -> ImagesOnlyScanDetails(
                images = images,
                onImageClick = onImageClick
            )

            PdfAndImagesTabs.PDF -> PdfOnlyScanDetails(document = document)
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    ScandroidTheme {
        ScanDetails(
            scan = SavedScan(
                id = UUID.randomUUID(),
                name = "Name",
                description = "Scan description",
                createdAt = ZonedDateTime.of(2024, 10, 13, 11, 23, 45, 0, ZoneId.of("UTC")),
                updatedAt = ZonedDateTime.of(2024, 10, 13, 11, 23, 45, 0, ZoneId.of("UTC")),
                lastAccessedAt = ZonedDateTime.of(2024, 10, 13, 11, 23, 45, 0, ZoneId.of("UTC")),
                files = SavedScanFiles.PdfAndImages(
                    pdfFile = File("path"),
                    imageFiles = listOf(File("path"))
                )
            ),
            onImageClick = { _, _ -> },
            onFileTypePageChange = { }
        )
    }
}
