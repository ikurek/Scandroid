package com.ikurek.scandroid.features.settings.ui.settings.builder

import com.ikurek.scandroid.core.design.components.dialogs.SingleSelectionDialogOption
import com.ikurek.scandroid.core.translations.Translations
import com.ikurek.scandroid.features.settings.data.model.ScannerFormats
import com.ikurek.scandroid.features.settings.data.model.ScannerMode
import com.ikurek.scandroid.features.settings.ui.settings.SettingsDialog
import com.ikurek.scandroid.features.settings.usecase.GetScannerSettings
import javax.inject.Inject
import com.ikurek.scandroid.core.translations.R as TranslationsR

internal class SettingsDialogBuilder @Inject constructor(
    private val getScannerSettings: GetScannerSettings,
    private val translations: Translations
) {

    suspend fun buildScannerModeSelectionDialog() = SettingsDialog.ScannerModeSelection(
        title = translations.getString(TranslationsR.string.settings_item_scanner_mode_title),
        selectedValue = getScannerSettings().scannerMode,
        availableOptions = ScannerMode.entries.map { scannerMode ->
            SingleSelectionDialogOption(
                value = scannerMode,
                text = when (scannerMode) {
                    ScannerMode.Base ->
                        translations.getString(TranslationsR.string.setting_scanner_mode_base_text)

                    ScannerMode.BaseWithFilter ->
                        translations.getString(TranslationsR.string.setting_scanner_mode_base_with_filters_text)

                    ScannerMode.Full ->
                        translations.getString(TranslationsR.string.setting_scanner_mode_full_text)
                },
                description = when (scannerMode) {
                    ScannerMode.Base ->
                        translations.getString(TranslationsR.string.setting_scanner_mode_base_description)

                    ScannerMode.BaseWithFilter ->
                        translations.getString(TranslationsR.string.setting_scanner_mode_base_with_filters_description)

                    ScannerMode.Full ->
                        translations.getString(TranslationsR.string.setting_scanner_mode_full_description)
                }
            )
        }.toSet()
    )

    suspend fun buildScannerFormatsSelectionDialog() = SettingsDialog.ScannerFormatsSelection(
        title = translations.getString(TranslationsR.string.settings_item_scanner_formats_title),
        selectedValue = getScannerSettings().scannerFormats,
        availableOptions = ScannerFormats.entries.map { scannerFormats ->
            SingleSelectionDialogOption(
                value = scannerFormats,
                text = when (scannerFormats) {
                    ScannerFormats.JpegAndPdf ->
                        translations.getString(TranslationsR.string.setting_scanner_formats_jpeg_and_pdf_text)

                    ScannerFormats.JpegOnly ->
                        translations.getString(TranslationsR.string.setting_scanner_formats_jpeg_only_text)

                    ScannerFormats.PdfOnly ->
                        translations.getString(TranslationsR.string.setting_scanner_formats_pdf_only_text)
                },
            )
        }.toSet()
    )
}
