package com.ikurek.scandroid.features.createscan.ui.newscan

import java.util.UUID

internal sealed interface NewScanSideEffect {
    data class ScanCreated(val scanId: UUID) : NewScanSideEffect
}
