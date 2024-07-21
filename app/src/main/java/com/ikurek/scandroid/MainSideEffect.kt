package com.ikurek.scandroid

internal sealed interface MainSideEffect {

    data object StartDocumentScanner : MainSideEffect

    data object OpenNewScanScreen : MainSideEffect
}
