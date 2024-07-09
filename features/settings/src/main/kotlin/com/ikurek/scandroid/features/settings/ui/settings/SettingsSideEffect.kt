package com.ikurek.scandroid.features.settings.ui.settings

internal sealed interface SettingsSideEffect {

    data object AboutClicked : SettingsSideEffect
}
