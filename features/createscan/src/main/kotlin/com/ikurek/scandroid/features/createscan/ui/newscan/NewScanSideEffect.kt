package com.ikurek.scandroid.features.createscan.ui.newscan

internal sealed interface NewScanSideEffect {
    data class ScanCreated(val scanId: String) : NewScanSideEffect
}
