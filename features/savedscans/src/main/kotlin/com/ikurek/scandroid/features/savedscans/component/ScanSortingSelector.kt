package com.ikurek.scandroid.features.savedscans.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.features.savedscans.model.SortingMode
import com.ikurek.scandroid.core.translations.R as TranslationsR

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ScanSortingSelector(
    selectedSortingMode: SortingMode,
    onSortingModeClick: (sortingMode: SortingMode) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(TranslationsR.string.saved_scans_sorting_mode_sort_by_label),
            modifier = Modifier.padding(horizontal = 24.dp),
            style = MaterialTheme.typography.labelLarge
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 24.dp)
        ) {
            itemsIndexed(
                items = SortingMode.entries.sortedByDescending { it == selectedSortingMode },
                key = { index, sortingMode -> sortingMode }
            ) { index, sortingMode ->
                SortingModeChip(
                    isSelected = sortingMode == selectedSortingMode,
                    sortingMode = sortingMode,
                    onSortingModeClick = onSortingModeClick,
                    modifier = Modifier.animateItemPlacement()
                )
            }
        }
    }
}

@Composable
private fun SortingModeChip(
    isSelected: Boolean,
    sortingMode: SortingMode,
    onSortingModeClick: (sortingMode: SortingMode) -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = isSelected,
        onClick = { onSortingModeClick(sortingMode) },
        label = {
            Text(
                text = stringResource(
                    id = when (sortingMode) {
                        SortingMode.Alphabetical -> TranslationsR.string.saved_scans_sorting_mode_alphabetical
                        SortingMode.Newest -> TranslationsR.string.saved_scans_sorting_mode_newest
                        SortingMode.Oldest -> TranslationsR.string.saved_scans_sorting_mode_oldest
                        SortingMode.RecentlyViewed -> TranslationsR.string.saved_scans_sorting_mode_recently_viewed
                    }
                )
            )
        },
        modifier = modifier
    )
}

@PreviewLightDark
@Composable
private fun Preview() {
    ScandroidTheme {
        ScanSortingSelector(
            selectedSortingMode = SortingMode.Alphabetical,
            onSortingModeClick = {}
        )
    }
}
