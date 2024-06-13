package com.ikurek.scandroid.features.settings.ui.settings.model

import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.collections.immutable.ImmutableList

interface SettingsListItem {

    data class Section(
        val name: String,
        val icon: ImageVector,
        val items: ImmutableList<SettingsItem>
    ) : SettingsListItem

    interface SettingsItem : SettingsListItem {
        val name: String
        val description: String?
        val type: Any

        data class Clickable(
            override val name: String,
            override val description: String?,
            override val type: ClickableSetting
        ) : SettingsItem

        data class Switchable(
            override val name: String,
            override val description: String?,
            override val type: SwitchableSetting,
            val isEnabled: Boolean
        ) : SettingsItem
    }
}
