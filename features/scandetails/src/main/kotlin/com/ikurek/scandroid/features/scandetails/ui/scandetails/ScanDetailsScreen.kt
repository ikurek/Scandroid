package com.ikurek.scandroid.features.scandetails.ui.scandetails

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SdCardAlert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.core.design.components.placeholders.ScreenPlaceholder
import com.ikurek.scandroid.core.translations.R
import com.ikurek.scandroid.features.savedscans.data.model.SavedScan
import com.ikurek.scandroid.features.savedscans.data.model.SavedScanFiles
import com.ikurek.scandroid.features.scandetails.ui.scandetails.component.ScanDetails
import com.ikurek.scandroid.features.scandetails.ui.scandetails.model.SavedScanState
import java.io.File
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID

@Composable
internal fun ScanDetailsScreen(
    scanState: SavedScanState,
    onImageClick: (File) -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(scanState) }
    ) { contentPadding ->
        Crossfade(
            targetState = scanState,
            modifier = Modifier.padding(contentPadding),
            label = "ScanDetailsScreen_Crossfade"
        ) { state ->
            Box(modifier = Modifier.fillMaxSize()) {
                when (state) {
                    is SavedScanState.Error -> ErrorPlaceholder(
                        modifier = Modifier.align(Alignment.Center)
                    )

                    is SavedScanState.Loaded -> ScanDetails(
                        scan = state.scan,
                        modifier = Modifier.fillMaxSize(),
                        onImageClick = onImageClick
                    )

                    is SavedScanState.Loading -> CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(scanState: SavedScanState) {
    CenterAlignedTopAppBar(
        title = {
            (scanState as? SavedScanState.Loaded)?.let { state ->
                Text(text = state.scan.name)
            }
        }
    )
}

@Composable
private fun ErrorPlaceholder(modifier: Modifier = Modifier) {
    ScreenPlaceholder(
        modifier = modifier,
        imageVector = Icons.Default.SdCardAlert,
        imageContentDescription = stringResource(
            id = R.string.scan_details_general_error_placeholder_content_descriptions
        ),
        label = stringResource(
            id = R.string.scan_details_general_error_placeholder_label
        ),
    )
}

@PreviewLightDark
@Composable
private fun PreviewLoading() {
    ScandroidTheme {
        ScanDetailsScreen(
            scanState = SavedScanState.Loading,
            onImageClick = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewLoaded() {
    ScandroidTheme {
        ScanDetailsScreen(
            scanState = SavedScanState.Loaded(
                scan = SavedScan(
                    id = UUID.randomUUID(),
                    name = "Name",
                    description = "Scan description",
                    createdAt = ZonedDateTime.of(2024, 10, 13, 11, 23, 45, 0, ZoneId.of("UTC")),
                    files = SavedScanFiles.PdfAndImages(
                        pdfFile = File("path"),
                        imageFiles = listOf(File("path"))
                    )
                )
            ),
            onImageClick = {}
        )
    }
}
