package com.ikurek.scandroid.features.savedscans.model

internal sealed interface UnsavedScanState {

    data object Present : UnsavedScanState

    data object Empty : UnsavedScanState
}
