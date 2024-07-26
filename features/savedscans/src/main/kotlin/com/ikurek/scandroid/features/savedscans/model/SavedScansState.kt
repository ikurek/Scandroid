package com.ikurek.scandroid.features.savedscans.model

import com.ikurek.scandroid.core.design.patterns.savedscans.SavedScanListItem

internal sealed interface SavedScansState {

    data object Loading : SavedScansState

    data object Empty : SavedScansState

    data class Loaded(val listItems: List<SavedScanListItem>) : SavedScansState

    data object Error : SavedScansState
}
