package com.ikurek.scandroid.features.search.ui

import com.ikurek.scandroid.core.design.patterns.savedscans.SavedScanListItem

internal sealed interface SearchState {
    val query: String

    data object NotSearching : SearchState {
        override val query: String = ""
    }

    data class NothingFound(override val query: String) : SearchState

    data class Found(override val query: String, val scans: List<SavedScanListItem>) : SearchState
}
