package com.ikurek.scandroid.features.scandetails.ui.scandetails

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.SdCardAlert
import androidx.compose.material.icons.filled.Share
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
import com.ikurek.scandroid.core.design.components.appbar.BottomAppBarAction
import com.ikurek.scandroid.core.design.components.appbar.PrimaryBottomAppBar
import com.ikurek.scandroid.core.design.components.appbar.PrimaryTopAppBar
import com.ikurek.scandroid.core.design.components.dialogs.ConfirmationDialog
import com.ikurek.scandroid.core.design.components.placeholders.ScreenPlaceholder
import com.ikurek.scandroid.core.design.patterns.filetypeselection.FileTypeSelectionDialog
import com.ikurek.scandroid.core.design.patterns.filetypeselection.SelectableFileType
import com.ikurek.scandroid.core.translations.R
import com.ikurek.scandroid.features.savedscans.data.model.SavedScan
import com.ikurek.scandroid.features.savedscans.data.model.SavedScanFiles
import com.ikurek.scandroid.features.scandetails.ui.scandetails.component.ExtendedScanInfoDialog
import com.ikurek.scandroid.features.scandetails.ui.scandetails.component.ScanDetails
import com.ikurek.scandroid.features.scandetails.ui.scandetails.model.PdfAndImagesTabs
import com.ikurek.scandroid.features.scandetails.ui.scandetails.model.SavedScanState
import java.io.File
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID
import com.ikurek.scandroid.core.translations.R as TranslationsR

@Composable
internal fun ScanDetailsScreen(
    scanState: SavedScanState,
    dialog: ScanDetailsDialog?,
    onOpenFileTypeSelect: (SelectableFileType) -> Unit,
    onShareFileTypeSelect: (SelectableFileType) -> Unit,
    onDeleteScanClick: () -> Unit,
    onScanInfoClick: () -> Unit,
    onOpenOutsideClick: () -> Unit,
    onShareFilesClick: () -> Unit,
    onConfirmDeleteScanClick: () -> Unit,
    onImageClick: (scanId: UUID, imageIndex: Int) -> Unit,
    onFileTypePageChange: (currentTab: PdfAndImagesTabs) -> Unit,
    onDismissDialog: () -> Unit,
    onNavigateUp: () -> Unit
) {
    dialog?.let {
        ScanDetailsDialog(
            dialog = dialog,
            onOpenFileTypeSelect = onOpenFileTypeSelect,
            onShareFileTypeSelect = onShareFileTypeSelect,
            onConfirmDeleteScanClick = onConfirmDeleteScanClick,
            onDismiss = onDismissDialog
        )
    }
    Scaffold(
        topBar = {
            ScanNameTopAppBar(
                scanState = scanState,
                onNavigateUp = onNavigateUp
            )
        },
        bottomBar = {
            ScanDetailsBottomBar(
                scanState = scanState,
                onDeleteScanClick = onDeleteScanClick,
                onScanInfoClick = onScanInfoClick,
                onOpenOutsideClick = onOpenOutsideClick,
                onShareFilesClick = onShareFilesClick
            )
        },
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
                        onImageClick = onImageClick,
                        onFileTypePageChange = onFileTypePageChange,
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
private fun ScanDetailsDialog(
    dialog: ScanDetailsDialog,
    onOpenFileTypeSelect: (SelectableFileType) -> Unit,
    onShareFileTypeSelect: (SelectableFileType) -> Unit,
    onConfirmDeleteScanClick: () -> Unit,
    onDismiss: () -> Unit
) {
    when (dialog) {
        ScanDetailsDialog.OpenFileTypeSelection -> FileTypeSelectionDialog(
            onFileTypeSelect = onOpenFileTypeSelect,
            onDismiss = onDismiss
        )

        ScanDetailsDialog.ShareFileTypeSelection -> FileTypeSelectionDialog(
            onFileTypeSelect = onShareFileTypeSelect,
            onDismiss = onDismiss
        )

        ScanDetailsDialog.DeleteScanConfirmation -> ConfirmationDialog(
            title = stringResource(TranslationsR.string.dialog_delete_scan_title),
            content = stringResource(TranslationsR.string.dialog_delete_scan_content),
            icon = Icons.Default.Delete,
            onConfirmRequest = onConfirmDeleteScanClick,
            onDismissRequest = onDismiss
        )

        is ScanDetailsDialog.ExtendedScanInformation -> ExtendedScanInfoDialog(
            extendedScanInfo = dialog.extendedScanInfo,
            onDismiss = onDismiss
        )
    }
}

@Composable
private fun ScanNameTopAppBar(scanState: SavedScanState, onNavigateUp: () -> Unit) {
    val title = (scanState as? SavedScanState.Loaded)?.scan?.name
    PrimaryTopAppBar(title = title, onNavigateUp = onNavigateUp)
}

@Composable
private fun ScanDetailsBottomBar(
    scanState: SavedScanState,
    onDeleteScanClick: () -> Unit,
    onScanInfoClick: () -> Unit,
    onOpenOutsideClick: () -> Unit,
    onShareFilesClick: () -> Unit,
) {
    if (scanState is SavedScanState.Loaded) {
        PrimaryBottomAppBar(
            actions = {
                BottomAppBarAction(
                    icon = Icons.Default.Delete,
                    text = stringResource(TranslationsR.string.scan_details_action_delete_label),
                    contentDescriptionRes = TranslationsR.string.scan_details_action_delete_content_descriptions,
                    onClick = onDeleteScanClick
                )

                Spacer(modifier = Modifier.width(16.dp))

                BottomAppBarAction(
                    icon = Icons.Default.Info,
                    text = stringResource(TranslationsR.string.scan_details_action_extended_info_label),
                    contentDescriptionRes = TranslationsR.string.scan_details_action_info_content_descriptions,
                    onClick = onScanInfoClick
                )

                Spacer(modifier = Modifier.width(16.dp))

                BottomAppBarAction(
                    icon = Icons.Default.FileOpen,
                    text = stringResource(TranslationsR.string.scan_details_action_open_outside_label),
                    contentDescriptionRes = TranslationsR.string.scan_details_action_open_outside_content_descriptions,
                    onClick = onOpenOutsideClick
                )
            },
            floatingActionButton = {
                ShareFab(onClick = onShareFilesClick)
            }
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
            id = R.string.common_general_error_placeholder_content_descriptions
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
            dialog = null,
            scanState = SavedScanState.Loading,
            onOpenFileTypeSelect = { },
            onShareFileTypeSelect = { },
            onConfirmDeleteScanClick = { },
            onDeleteScanClick = { },
            onScanInfoClick = { },
            onOpenOutsideClick = { },
            onShareFilesClick = { },
            onImageClick = { _, _ -> },
            onFileTypePageChange = { },
            onDismissDialog = { },
            onNavigateUp = { }
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewLoaded() {
    ScandroidTheme {
        ScanDetailsScreen(
            dialog = null,
            scanState = SavedScanState.Loaded(
                scan = SavedScan(
                    id = UUID.randomUUID(),
                    name = "Name",
                    description = "Scan description",
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
                        imageFiles = listOf(File("path"))
                    )
                )
            ),
            onOpenFileTypeSelect = { },
            onShareFileTypeSelect = { },
            onConfirmDeleteScanClick = { },
            onDeleteScanClick = { },
            onScanInfoClick = { },
            onOpenOutsideClick = { },
            onShareFilesClick = { },
            onImageClick = { _, _ -> },
            onFileTypePageChange = { },
            onDismissDialog = { },
            onNavigateUp = { }
        )
    }
}
