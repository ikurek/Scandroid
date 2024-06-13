package com.ikurek.scandroid.features.settings.ui.settings.preview

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Scanner
import com.ikurek.scandroid.features.settings.ui.settings.model.ClickableSetting
import com.ikurek.scandroid.features.settings.ui.settings.model.SettingsListItem
import com.ikurek.scandroid.features.settings.ui.settings.model.SwitchableSetting
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Suppress("MaxLineLength")
internal fun settingsListItems() = persistentListOf(
    SettingsListItem.Section(
        name = "Scanner settings",
        icon = Icons.Filled.Scanner,
        items = persistentListOf(
            SettingsListItem.SettingsItem.Clickable(
                name = "Scanner mode",
                description = "Setting description for changing the scanner mode available in app",
                type = ClickableSetting.ScannerMode
            ),
            SettingsListItem.SettingsItem.Clickable(
                name = "Scanner formats",
                description = "Setting description for changing the formats scanner should use when saving new scans",
                type = ClickableSetting.ScannerFileFormats
            )
        ).toImmutableList()
    ),
    SettingsListItem.SettingsItem.Clickable(
        name = "About",
        description = "Setting description for changing the scanner mode available in app",
        type = ClickableSetting.About
    ),
    SettingsListItem.SettingsItem.Switchable(
        name = "Enable analytics",
        description = "Opt in or opt out of all analytics recorded by the app",
        type = SwitchableSetting.AnalyticsEnabled,
        isEnabled = true
    )
).toImmutableList()
