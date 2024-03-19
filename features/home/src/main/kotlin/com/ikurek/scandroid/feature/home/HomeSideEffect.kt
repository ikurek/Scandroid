package com.ikurek.scandroid.feature.home

import com.ikurek.scandroid.features.createscan.data.model.ScannerSettings

internal sealed interface HomeSideEffect {

    data class StartDocumentScanner(val settings: ScannerSettings) : HomeSideEffect

    data object OpenNewScanScreen : HomeSideEffect
}
