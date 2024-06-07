package com.ikurek.scandroid.features.savedscans.util

import com.ikurek.scandroid.features.savedscans.data.model.SavedScan
import com.ikurek.scandroid.features.savedscans.model.SortingMode

internal fun List<SavedScan>.sort(sortingMode: SortingMode) = when (sortingMode) {
    SortingMode.Alphabetical -> sortedBy { it.name }
    SortingMode.Newest -> sortedByDescending { it.createdAt }
    SortingMode.Oldest -> sortedBy { it.createdAt }
    SortingMode.RecentlyViewed -> sortedBy { it.lastAccessedAt }
}
