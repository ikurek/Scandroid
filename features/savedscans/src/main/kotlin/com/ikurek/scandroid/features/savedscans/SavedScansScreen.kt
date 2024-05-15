package com.ikurek.scandroid.features.savedscans

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Scanner
import androidx.compose.material.icons.filled.SdCardAlert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import com.ikurek.scandroid.features.savedscans.component.SavedScanList
import com.ikurek.scandroid.features.savedscans.component.UnsavedScansCard
import com.ikurek.scandroid.features.savedscans.data.model.SavedScan
import com.ikurek.scandroid.features.savedscans.data.model.SavedScanFiles
import com.ikurek.scandroid.features.savedscans.model.SavedScansState
import com.ikurek.scandroid.features.savedscans.model.UnsavedScanState
import java.io.File
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID
import com.ikurek.scandroid.core.translations.R as TranslationsR

@Composable
internal fun SavedScansScreen(
    unsavedScanState: UnsavedScanState,
    scansState: SavedScansState,
    onRestoreUnsavedScanClick: () -> Unit,
    onDeleteUnsavedScanClick: () -> Unit,
    onScanClick: (UUID) -> Unit,
    onCreateScanClick: () -> Unit,
) {
    Scaffold(
        topBar = { PrimaryTopAppBar(title = stringResource(TranslationsR.string.app_name)) },
        floatingActionButton = { CreateScanFloatingActionButton(onClick = onCreateScanClick) }
    ) { contentPadding ->
        Crossfade(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
            targetState = scansState,
            label = "SavedScansScreen_Crossfade"
        ) { state ->
            Box(modifier = Modifier.fillMaxSize()) {
                when (state) {
                    is SavedScansState.Loading -> CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )

                    is SavedScansState.Empty -> {
                        UnsavedScansPopup(
                            modifier = Modifier.align(Alignment.TopCenter),
                            unsavedScanState = unsavedScanState,
                            onRestoreUnsavedScanClick = onRestoreUnsavedScanClick,
                            onDeleteUnsavedScanClick = onDeleteUnsavedScanClick
                        )

                        EmptyScreenPlaceholder(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    is SavedScansState.Loaded -> SavedScansContent(
                        modifier = Modifier.fillMaxSize(),
                        unsavedScanState = unsavedScanState,
                        scans = state.scans,
                        onRestoreUnsavedScanClick = onRestoreUnsavedScanClick,
                        onDeleteUnsavedScanClick = onDeleteUnsavedScanClick,
                        onScanClick = onScanClick
                    )

                    is SavedScansState.Error -> ErrorScreenPlaceholder(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
private fun CreateScanFloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.DocumentScanner,
            contentDescription = stringResource(
                id = TranslationsR.string.saved_scans_create_scan_fab_content_descriptions
            ),
        )
    }
}

@Composable
private fun UnsavedScansPopup(
    unsavedScanState: UnsavedScanState,
    onRestoreUnsavedScanClick: () -> Unit,
    onDeleteUnsavedScanClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        modifier = modifier.padding(horizontal = 16.dp),
        visible = unsavedScanState is UnsavedScanState.Present,
        label = "UnsavedScansPopup_AnimatedVisibility",
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        UnsavedScansCard(
            onClick = onRestoreUnsavedScanClick,
            onDeleteClick = onDeleteUnsavedScanClick
        )
    }
}

@Composable
private fun SavedScansContent(
    unsavedScanState: UnsavedScanState,
    scans: List<SavedScan>,
    onRestoreUnsavedScanClick: () -> Unit,
    onDeleteUnsavedScanClick: () -> Unit,
    onScanClick: (UUID) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        UnsavedScansPopup(
            unsavedScanState = unsavedScanState,
            onRestoreUnsavedScanClick = onRestoreUnsavedScanClick,
            onDeleteUnsavedScanClick = onDeleteUnsavedScanClick
        )
        SavedScanList(
            modifier = Modifier.fillMaxSize(),
            scans = scans,
            onScanClick = onScanClick
        )
    }
}

@Composable
private fun EmptyScreenPlaceholder(modifier: Modifier = Modifier) {
    ScreenPlaceholder(
        modifier = modifier,
        imageVector = Icons.Default.Scanner,
        imageContentDescription = stringResource(
            id = R.string.saved_scans_empty_placeholder_content_descriptions
        ),
        label = stringResource(
            id = R.string.saved_scans_empty_placeholder_label
        ),
    )
}

@Composable
private fun ErrorScreenPlaceholder(modifier: Modifier = Modifier) {
    ScreenPlaceholder(
        modifier = modifier,
        imageVector = Icons.Default.SdCardAlert,
        imageContentDescription = stringResource(
            id = R.string.saved_scans_general_error_placeholder_content_descriptions
        ),
        label = stringResource(
            id = R.string.saved_scans_general_error_placeholder_label
        ),
    )
}

@PreviewLightDark
@Composable
private fun PreviewLoaded() {
    ScandroidTheme {
        SavedScansScreen(
            unsavedScanState = UnsavedScanState.Present,
            scansState = SavedScansState.Loaded(
                listOf(
                    SavedScan(
                        id = UUID.randomUUID(),
                        name = "PDF Scan",
                        description = "Scan description for PDF scan",
                        createdAt = ZonedDateTime.of(2024, 10, 13, 11, 23, 45, 0, ZoneId.of("UTC")),
                        files = SavedScanFiles.PdfOnly(pdfFile = File("path"))
                    ),
                    SavedScan(
                        id = UUID.randomUUID(),
                        name = "Image Scan",
                        description = "Scan description for image scan",
                        createdAt = ZonedDateTime.of(2024, 10, 13, 11, 23, 45, 0, ZoneId.of("UTC")),
                        files = SavedScanFiles.ImagesOnly(imageFiles = listOf(File("path")))
                    ),
                    SavedScan(
                        id = UUID.randomUUID(),
                        name = "Image & PDF Scan",
                        description = "Scan description for image & PDF scan",
                        createdAt = ZonedDateTime.of(2024, 10, 13, 11, 23, 45, 0, ZoneId.of("UTC")),
                        files = SavedScanFiles.PdfAndImages(
                            pdfFile = File("path"),
                            imageFiles = listOf(File("path"), File("path"))
                        )
                    )
                )
            ),
            onRestoreUnsavedScanClick = {},
            onDeleteUnsavedScanClick = {},
            onScanClick = {},
            onCreateScanClick = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewEmpty() {
    ScandroidTheme {
        SavedScansScreen(
            unsavedScanState = UnsavedScanState.Present,
            scansState = SavedScansState.Empty,
            onRestoreUnsavedScanClick = {},
            onDeleteUnsavedScanClick = {},
            onScanClick = {},
            onCreateScanClick = {}
        )
    }
}
