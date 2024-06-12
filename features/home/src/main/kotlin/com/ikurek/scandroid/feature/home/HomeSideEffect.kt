package com.ikurek.scandroid.feature.home

internal sealed interface HomeSideEffect {

    data object StartDocumentScanner : HomeSideEffect

    data object OpenNewScanScreen : HomeSideEffect
}
