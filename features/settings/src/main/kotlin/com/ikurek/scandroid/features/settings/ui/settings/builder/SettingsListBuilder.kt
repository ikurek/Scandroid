package com.ikurek.scandroid.features.settings.ui.settings.builder

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.MiscellaneousServices
import androidx.compose.material.icons.filled.Scanner
import com.ikurek.scandroid.core.translations.Translations
import com.ikurek.scandroid.features.settings.ui.settings.model.ClickableSetting
import com.ikurek.scandroid.features.settings.ui.settings.model.SettingsListItem
import com.ikurek.scandroid.features.settings.ui.settings.model.SwitchableSetting
import com.ikurek.scandroid.features.settings.usecase.GetAnalyticsStatus
import javax.inject.Inject
import com.ikurek.scandroid.core.translations.R as TranslationsR

internal class SettingsListBuilder @Inject constructor(
    private val translations: Translations,
    private val getAnalyticsStatus: GetAnalyticsStatus
) {

    suspend fun build(): List<SettingsListItem> = buildList {
        addScannerSection()
        addAnalyticsSection()
        addOtherSection()
    }

    private fun MutableList<SettingsListItem>.addScannerSection() = add(
        SettingsListItem.Section(
            name = translations.getString(TranslationsR.string.settings_section_scanner),
            icon = Icons.Filled.Scanner,
            items = listOf(
                SettingsListItem.SettingsItem.Clickable(
                    name = translations.getString(TranslationsR.string.settings_item_scanner_mode_title),
                    description = translations.getString(TranslationsR.string.settings_item_scanner_mode_description),
                    type = ClickableSetting.ScannerMode
                ),
                SettingsListItem.SettingsItem.Clickable(
                    name = translations.getString(TranslationsR.string.settings_item_scanner_formats_title),
                    description = translations.getString(
                        TranslationsR.string.settings_item_scanner_formats_description
                    ),
                    type = ClickableSetting.ScannerFileFormats
                )
            )
        )
    )

    private suspend fun MutableList<SettingsListItem>.addAnalyticsSection() {
        val analyticsStatus = getAnalyticsStatus()

        add(
            SettingsListItem.Section(
                name = translations.getString(TranslationsR.string.settings_section_analytics),
                icon = Icons.Filled.Analytics,
                items = listOf(
                    SettingsListItem.SettingsItem.Switchable(
                        name = translations.getString(TranslationsR.string.settings_item_enable_analytics_title),
                        description = translations.getString(
                            TranslationsR.string.settings_item_enable_analytics_description
                        ),
                        type = SwitchableSetting.AnalyticsEnabled,
                        isEnabled = analyticsStatus.areAnalyticsEnabled
                    ),
                    SettingsListItem.SettingsItem.Switchable(
                        name = translations.getString(TranslationsR.string.settings_item_enable_crashlytics_title),
                        description = translations.getString(
                            TranslationsR.string.settings_item_enable_crashlytics_description
                        ),
                        type = SwitchableSetting.CrashlyticsEnabled,
                        isEnabled = analyticsStatus.areCrashlyticsEnabled
                    ),
                    SettingsListItem.SettingsItem.Switchable(
                        name = translations.getString(
                            TranslationsR.string.settings_item_enable_performance_monitoring_title
                        ),
                        description = translations.getString(
                            TranslationsR.string.settings_item_enable_performance_monitoring_description
                        ),
                        type = SwitchableSetting.PerformanceMonitoringEnabled,
                        isEnabled = analyticsStatus.isPerformanceMonitoringEnabled
                    )
                )
            )
        )
    }

    private fun MutableList<SettingsListItem>.addOtherSection() = add(
        SettingsListItem.Section(
            name = translations.getString(TranslationsR.string.settings_section_other),
            icon = Icons.Default.MiscellaneousServices,
            items = listOf(
                SettingsListItem.SettingsItem.Clickable(
                    name = translations.getString(TranslationsR.string.settings_item_rate_app_title),
                    description = translations.getString(TranslationsR.string.settings_item_rate_app_description),
                    type = ClickableSetting.RateApp
                ),
                SettingsListItem.SettingsItem.Clickable(
                    name = translations.getString(TranslationsR.string.settings_item_about_title),
                    description = translations.getString(TranslationsR.string.settings_item_about_description),
                    type = ClickableSetting.About
                )
            )
        )
    )
}
