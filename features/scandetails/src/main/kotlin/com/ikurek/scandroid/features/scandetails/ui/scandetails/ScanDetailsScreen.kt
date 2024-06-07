package com.ikurek.scandroid.features.scandetails.ui.scandetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.SdCardAlert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.core.design.components.appbar.PrimaryTopAppBar
import com.ikurek.scandroid.core.design.components.placeholders.ScreenPlaceholder
import com.ikurek.scandroid.core.translations.R
import com.ikurek.scandroid.features.savedscans.data.model.SavedScan
import com.ikurek.scandroid.features.savedscans.data.model.SavedScanFiles
import com.ikurek.scandroid.features.scandetails.ui.scandetails.component.ScanDetails
import com.ikurek.scandroid.features.scandetails.ui.scandetails.model.PdfAndImagesTabs
import com.ikurek.scandroid.features.scandetails.ui.scandetails.model.SavedScanState
import com.ikurek.scandroid.features.scandetails.ui.scandetails.model.ScanAction
import java.io.File
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID
import com.ikurek.scandroid.core.translations.R as TranslationsR

@Composable
internal fun ScanDetailsScreen(
    scanState: SavedScanState,
    availableScanActions: List<ScanAction>,
    onOpenPdfOutsideClick: (files: SavedScanFiles) -> Unit,
    onShareFilesClick: (files: SavedScanFiles) -> Unit,
    onImageClick: (scanId: UUID, imageIndex: Int) -> Unit,
    onFileTypePageChange: (currentTab: PdfAndImagesTabs) -> Unit,
    onNavigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            ScanNameTopAppBar(
                scanState = scanState,
                onNavigateUp = onNavigateUp
            )
        },
        floatingActionButton = {
            ScanActionsFloatingActionButton(
                savedScanState = scanState,
                availableScanActions = availableScanActions,
                onOpenPdfOutsideClick = onOpenPdfOutsideClick,
                onShareFilesClick = onShareFilesClick
            )
        }
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
                        onImageClick = onImageClick,
                        onFileTypePageChange = onFileTypePageChange
                    )

                    is SavedScanState.Loading -> CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
private fun ScanNameTopAppBar(scanState: SavedScanState, onNavigateUp: () -> Unit) {
    val title = (scanState as? SavedScanState.Loaded)?.scan?.name
    PrimaryTopAppBar(title = title, onNavigateUp = onNavigateUp)
}

@Composable
private fun ScanActionsFloatingActionButton(
    savedScanState: SavedScanState,
    availableScanActions: List<ScanAction>,
    onOpenPdfOutsideClick: (files: SavedScanFiles) -> Unit,
    onShareFilesClick: (files: SavedScanFiles) -> Unit,
) {
    if (savedScanState is SavedScanState.Loaded) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AnimatedVisibility(
                visible = availableScanActions.contains(ScanAction.OpenPdfOutside),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                OpenPdfOutsideFab(
                    onClick = { onOpenPdfOutsideClick(savedScanState.scan.files) }
                )
            }

            AnimatedVisibility(
                visible = availableScanActions.contains(ScanAction.Share),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                ShareFab(
                    onClick = { onShareFilesClick(savedScanState.scan.files) }
                )
            }
        }
    }
}

@Composable
private fun OpenPdfOutsideFab(onClick: () -> Unit) {
    SmallFloatingActionButton(onClick = onClick, modifier = Modifier.padding(bottom = 24.dp)) {
        Icon(
            imageVector = Icons.Default.FileOpen,
            contentDescription = stringResource(
                id = TranslationsR.string.scan_details_fab_open_outside_content_descriptions
            )
        )
    }
}

@Composable
private fun ShareFab(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = stringResource(
                id = TranslationsR.string.scan_details_fab_share_content_descriptions
            )
        )
    }
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
            availableScanActions = emptyList(),
            onOpenPdfOutsideClick = { },
            onShareFilesClick = { },
            onImageClick = { _, _ -> },
            onFileTypePageChange = { },
            onNavigateUp = { }
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
                    updatedAt = ZonedDateTime.of(2024, 10, 13, 11, 23, 45, 0, ZoneId.of("UTC")),
                    lastAccessedAt = ZonedDateTime.of(2024, 10, 13, 11, 23, 45, 0, ZoneId.of("UTC")),
                    files = SavedScanFiles.PdfAndImages(
                        pdfFile = File("path"),
                        imageFiles = listOf(File("path"))
                    )
                )
            ),
            availableScanActions = listOf(ScanAction.Share, ScanAction.OpenPdfOutside),
            onOpenPdfOutsideClick = { },
            onShareFilesClick = { },
            onImageClick = { _, _ -> },
            onFileTypePageChange = { },
            onNavigateUp = { }
        )
    }
}
