package com.ikurek.scandroid.features.createscan.ui.newscan.model

import com.ikurek.scandroid.features.settings.data.model.ScannerFormats

internal sealed interface FormatSelectorState {

    data object Hidden : FormatSelectorState

    data class Visible(val selectedScannerFormats: ScannerFormats) : FormatSelectorState
}
