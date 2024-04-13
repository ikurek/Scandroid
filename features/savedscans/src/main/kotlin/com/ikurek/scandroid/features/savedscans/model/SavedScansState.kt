package com.ikurek.scandroid.features.savedscans.model

import com.ikurek.scandroid.features.savedscans.data.model.SavedScan

sealed interface SavedScansState {

    data object Loading : SavedScansState

    data object Empty : SavedScansState

    data class Loaded(val scans: List<SavedScan>) : SavedScansState

    data object Error : SavedScansState
}
