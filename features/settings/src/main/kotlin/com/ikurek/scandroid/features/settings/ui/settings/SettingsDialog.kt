package com.ikurek.scandroid.features.settings.ui.settings

import com.ikurek.scandroid.core.design.components.dialogs.SingleSelectionDialogOption
import com.ikurek.scandroid.features.settings.data.model.ScannerFormats
import com.ikurek.scandroid.features.settings.data.model.ScannerMode

internal sealed interface SettingsDialog {

    data class ScannerModeSelection(
        val title: String,
        val selectedValue: ScannerMode,
        val availableOptions: Set<SingleSelectionDialogOption<ScannerMode>>
    ) : SettingsDialog

    data class ScannerFormatsSelection(
        val title: String,
        val selectedValue: ScannerFormats,
        val availableOptions: Set<SingleSelectionDialogOption<ScannerFormats>>
    ) : SettingsDialog
}
