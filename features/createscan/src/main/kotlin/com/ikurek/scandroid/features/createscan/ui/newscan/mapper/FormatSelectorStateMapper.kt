package com.ikurek.scandroid.features.createscan.ui.newscan.mapper

import com.ikurek.scandroid.features.createscan.data.model.ScannedDocuments
import com.ikurek.scandroid.features.createscan.ui.newscan.model.FormatSelectorState
import com.ikurek.scandroid.features.settings.data.model.ScannerFormats
import javax.inject.Inject

internal class FormatSelectorStateMapper @Inject constructor() {

    fun map(scannedDocuments: ScannedDocuments): FormatSelectorState {
        return if (scannedDocuments.hasMultipleDocumentFormats) {
            FormatSelectorState.Visible(ScannerFormats.Default)
        } else {
            FormatSelectorState.Hidden
        }
    }
}
