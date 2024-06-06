package com.ikurek.scandroid.features.scandetails.ui.scandetails.model

import com.ikurek.scandroid.features.savedscans.data.model.SavedScan
import com.ikurek.scandroid.features.savedscans.data.model.SavedScanFiles

sealed interface SavedScanState {
    data object Loading : SavedScanState

    data class Loaded(val scan: SavedScan) : SavedScanState

    data object Error : SavedScanState
}

internal val SavedScanState.availableScanActions: List<ScanAction>
    get() = when (this) {
        is SavedScanState.Loaded -> when (scan.files) {
            is SavedScanFiles.PdfOnly -> listOf(ScanAction.Share, ScanAction.OpenPdfOutside)
            else -> listOf(ScanAction.Share)
        }
        else -> emptyList()
    }
