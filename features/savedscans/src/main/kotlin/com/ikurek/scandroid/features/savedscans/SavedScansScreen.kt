package com.ikurek.scandroid.features.savedscans

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Scanner
import androidx.compose.material.icons.filled.SdCardAlert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.core.design.components.placeholders.ScreenPlaceholder
import com.ikurek.scandroid.core.translations.R
import com.ikurek.scandroid.features.savedscans.component.SavedScanList
import com.ikurek.scandroid.features.savedscans.component.ScanSortingSelector
import com.ikurek.scandroid.features.savedscans.component.UnsavedScansCard
import com.ikurek.scandroid.features.savedscans.model.SavedScanListItem
import com.ikurek.scandroid.features.savedscans.model.SavedScanListItem.SavedScanListItemFiles
import com.ikurek.scandroid.features.savedscans.model.SavedScansState
import com.ikurek.scandroid.features.savedscans.model.SortingMode
import com.ikurek.scandroid.features.savedscans.model.UnsavedScanState
import java.util.UUID
import com.ikurek.scandroid.core.translations.R as TranslationsR

@Composable
internal fun SavedScansScreen(
    unsavedScanState: UnsavedScanState,
    selectedSortingMode: SortingMode,
    scansState: SavedScansState,
    onRestoreUnsavedScanClick: () -> Unit,
    onDeleteUnsavedScanClick: () -> Unit,
    onSortingModeClick: (sortingMode: SortingMode) -> Unit,
    onScanClick: (UUID) -> Unit,
    onCreateScanClick: () -> Unit,
) {
    Crossfade(
        modifier = Modifier.fillMaxSize(),
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
                    unsavedScanState = unsavedScanState,
                    selectedSortingMode = selectedSortingMode,
                    listItems = state.listItems,
                    onRestoreUnsavedScanClick = onRestoreUnsavedScanClick,
                    onDeleteUnsavedScanClick = onDeleteUnsavedScanClick,
                    onSortingModeClick = onSortingModeClick,
                    onScanClick = onScanClick,
                    modifier = Modifier.fillMaxSize()
                )

                is SavedScansState.Error -> ErrorScreenPlaceholder(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            CreateScanFloatingActionButton(
                onClick = onCreateScanClick
            )
        }
    }
}

@Composable
private fun BoxScope.CreateScanFloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(16.dp)
    ) {
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
        modifier = modifier.padding(start = 24.dp, end = 24.dp, bottom = 16.dp),
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
    selectedSortingMode: SortingMode,
    listItems: List<SavedScanListItem>,
    onRestoreUnsavedScanClick: () -> Unit,
    onDeleteUnsavedScanClick: () -> Unit,
    onSortingModeClick: (sortingMode: SortingMode) -> Unit,
    onScanClick: (UUID) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        UnsavedScansPopup(
            unsavedScanState = unsavedScanState,
            onRestoreUnsavedScanClick = onRestoreUnsavedScanClick,
            onDeleteUnsavedScanClick = onDeleteUnsavedScanClick
        )

        ScanSortingSelector(
            selectedSortingMode = selectedSortingMode,
            onSortingModeClick = onSortingModeClick,
            modifier = Modifier.fillMaxWidth()
        )

        SavedScanList(
            items = listItems,
            onScanClick = onScanClick,
            modifier = Modifier.fillMaxSize()
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
            selectedSortingMode = SortingMode.RecentlyViewed,
            scansState = SavedScansState.Loaded(
                listItems = listOf(
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
                )
            ),
            onRestoreUnsavedScanClick = {},
            onDeleteUnsavedScanClick = {},
            onSortingModeClick = {},
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
            selectedSortingMode = SortingMode.Newest,
            scansState = SavedScansState.Empty,
            onRestoreUnsavedScanClick = {},
            onDeleteUnsavedScanClick = {},
            onSortingModeClick = {},
            onScanClick = {},
            onCreateScanClick = {}
        )
    }
}
