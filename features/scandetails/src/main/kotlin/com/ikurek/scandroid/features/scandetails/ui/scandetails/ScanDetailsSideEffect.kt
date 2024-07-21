package com.ikurek.scandroid.features.scandetails.ui.scandetails

internal sealed interface ScanDetailsSideEffect {

    data object ScanDeleted : ScanDetailsSideEffect
}
