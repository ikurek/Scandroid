package com.ikurek.scandroid.features.settings.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.core.design.components.appbar.PrimaryTopAppBar
import com.ikurek.scandroid.core.design.components.divider.DividerWithIcon
import com.ikurek.scandroid.features.settings.ui.settings.model.ClickableSetting
import com.ikurek.scandroid.features.settings.ui.settings.model.SettingsListItem
import com.ikurek.scandroid.features.settings.ui.settings.model.SettingsState
import com.ikurek.scandroid.features.settings.ui.settings.model.SwitchableSetting
import com.ikurek.scandroid.features.settings.ui.settings.preview.settingsListItems
import com.ikurek.scandroid.core.translations.R as TranslationsR

@Composable
internal fun SettingsScreen(
    settingsState: SettingsState,
    onSettingClick: (setting: ClickableSetting) -> Unit,
    onSettingSwitch: (setting: SwitchableSetting, isEnabled: Boolean) -> Unit,
    onNavigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            PrimaryTopAppBar(
                title = stringResource(TranslationsR.string.settings_title),
                onNavigateUp = onNavigateUp
            )
        }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            Content(
                settingsState = settingsState,
                onSettingClick = onSettingClick,
                onSettingSwitch = onSettingSwitch
            )
        }
    }
}

@Composable
private fun Content(
    settingsState: SettingsState,
    onSettingClick: (setting: ClickableSetting) -> Unit,
    onSettingSwitch: (setting: SwitchableSetting, isEnabled: Boolean) -> Unit,
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        settingsState.items.forEach { settingListItem ->
            when (settingListItem) {
                is SettingsListItem.Section -> SettingsSection(
                    section = settingListItem,
                    onSettingClick = onSettingClick,
                    onSettingSwitch = onSettingSwitch
                )

                is SettingsListItem.SettingsItem -> SettingItem(
                    setting = settingListItem,
                    onSettingClick = onSettingClick,
                    onSettingSwitch = onSettingSwitch
                )
            }
        }
    }
}

@Composable
private fun SettingsSection(
    section: SettingsListItem.Section,
    onSettingClick: (setting: ClickableSetting) -> Unit,
    onSettingSwitch: (setting: SwitchableSetting, isEnabled: Boolean) -> Unit,
) {
    DividerWithIcon(
        section.name,
        section.icon,
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
    )

    section.items.forEach { item ->
        SettingItem(item, onSettingClick, onSettingSwitch)
    }
}

@Composable
private fun SettingItem(
    setting: SettingsListItem.SettingsItem,
    onSettingClick: (setting: ClickableSetting) -> Unit,
    onSettingSwitch: (setting: SwitchableSetting, isEnabled: Boolean) -> Unit,
) {
    when (setting) {
        is SettingsListItem.SettingsItem.Clickable -> ClickableSettingItem(
            setting = setting,
            onClick = { onSettingClick(setting.type) }
        )

        is SettingsListItem.SettingsItem.Switchable -> SwitchableSettingItem(
            setting = setting,
            onSwitch = { isEnabled -> onSettingSwitch(setting.type, isEnabled) }
        )
    }
}

@Composable
private fun ClickableSettingItem(
    setting: SettingsListItem.SettingsItem.Clickable,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Text(
            text = setting.name,
            style = MaterialTheme.typography.titleMedium
        )
        setting.description?.let {
            Text(
                text = setting.description,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun SwitchableSettingItem(
    setting: SettingsListItem.SettingsItem.Switchable,
    onSwitch: (isEnabled: Boolean) -> Unit
) {
    Row(
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = setting.name,
                style = MaterialTheme.typography.titleMedium
            )
            setting.description?.let {
                Text(
                    text = setting.description,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Switch(checked = setting.isEnabled, onCheckedChange = onSwitch)
    }
}

@Suppress("MaxLineLength")
@PreviewLightDark
@Composable
private fun SettingsScreenPreview() {
    ScandroidTheme {
        SettingsScreen(
            settingsState = SettingsState(
                items = settingsListItems(),
            ),
            onSettingClick = {},
            onSettingSwitch = { _, _ -> },
            onNavigateUp = {}
        )
    }
}
