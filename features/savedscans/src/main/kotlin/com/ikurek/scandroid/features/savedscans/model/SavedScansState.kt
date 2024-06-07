package com.ikurek.scandroid.features.savedscans.model

import kotlinx.collections.immutable.ImmutableList

internal sealed interface SavedScansState {

    data object Loading : SavedScansState

    data object Empty : SavedScansState

    data class Loaded(val listItems: ImmutableList<SavedScanListItem>) : SavedScansState

    data object Error : SavedScansState
}
