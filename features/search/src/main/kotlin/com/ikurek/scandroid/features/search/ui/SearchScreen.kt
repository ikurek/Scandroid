package com.ikurek.scandroid.features.search.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ManageSearch
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.core.design.components.inputs.SearchInput
import com.ikurek.scandroid.core.design.components.placeholders.ScreenPlaceholder
import com.ikurek.scandroid.core.design.patterns.savedscans.SavedScanCard
import com.ikurek.scandroid.core.design.patterns.savedscans.SavedScanListItem
import com.ikurek.scandroid.core.design.patterns.savedscans.SavedScanListItem.SavedScanListItemFiles
import java.util.UUID
import com.ikurek.scandroid.core.translations.R as TranslationsR

@Composable
internal fun SearchScreen(
    state: SearchState,
    onQueryChange: (query: String) -> Unit,
    onScanClick: (id: UUID) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        SearchInput(
            label = stringResource(TranslationsR.string.search_scans_query_input_label),
            onValueChange = onQueryChange,
            initialValue = state.query,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp)
        )

        AnimatedContent(
            targetState = state,
            contentKey = { it::class },
            label = "SearchScreenAnimatedContent"
        ) { targetState ->
            when (targetState) {
                is SearchState.NotSearching -> NoQueryPlaceholder()
                is SearchState.NothingFound -> NoScansFoundPlaceholder(
                    query = state.query
                )

                is SearchState.Found -> ScanList(
                    items = targetState.scans,
                    onScanClick = onScanClick
                )
            }
        }
    }
}

@Composable
private fun NoScansFoundPlaceholder(query: String) {
    ScreenPlaceholder(
        imageVector = Icons.Default.SearchOff,
        imageContentDescription = stringResource(
            id = TranslationsR.string.search_nothing_found_placeholder_content_description
        ),
        label = stringResource(TranslationsR.string.search_nothing_found_placeholder_label, query),
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    )
}

@Composable
private fun NoQueryPlaceholder() {
    ScreenPlaceholder(
        imageVector = Icons.AutoMirrored.Default.ManageSearch,
        imageContentDescription = stringResource(
            id = TranslationsR.string.search_empty_placeholder_content_description
        ),
        label = stringResource(TranslationsR.string.search_empty_placeholder_label),
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    )
}

@Composable
private fun ScanList(items: List<SavedScanListItem>, onScanClick: (id: UUID) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 16.dp),
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
private fun PreviewNotSearching() {
    ScandroidTheme {
        SearchScreen(
            state = SearchState.NotSearching,
            onQueryChange = { },
            onScanClick = { }
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewNoResultsFound() {
    ScandroidTheme {
        SearchScreen(
            state = SearchState.NothingFound("1234"),
            onQueryChange = { },
            onScanClick = { }
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewResultsFound() {
    ScandroidTheme {
        SearchScreen(
            state = SearchState.Found(
                query = "Scan",
                scans = listOf(
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
            onQueryChange = { },
            onScanClick = { }
        )
    }
}
