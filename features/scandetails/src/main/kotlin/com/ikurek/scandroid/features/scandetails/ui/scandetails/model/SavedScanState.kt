package com.ikurek.scandroid.features.scandetails.ui.scandetails.model

import com.ikurek.scandroid.features.savedscans.data.model.SavedScan

sealed interface SavedScanState {
    data object Loading : SavedScanState

    data class Loaded(val scan: SavedScan) : SavedScanState

    data object Error : SavedScanState
}
