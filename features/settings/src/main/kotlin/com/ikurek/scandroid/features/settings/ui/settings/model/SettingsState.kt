package com.ikurek.scandroid.features.settings.ui.settings.model

import kotlinx.collections.immutable.ImmutableList

internal data class SettingsState(
    val items: ImmutableList<SettingsListItem>
)
