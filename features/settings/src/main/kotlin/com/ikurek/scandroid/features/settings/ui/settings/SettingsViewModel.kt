package com.ikurek.scandroid.features.settings.ui.settings

import androidx.lifecycle.ViewModel
import com.ikurek.scandroid.features.settings.ui.settings.model.ClickableSetting
import com.ikurek.scandroid.features.settings.ui.settings.model.SettingsState
import com.ikurek.scandroid.features.settings.ui.settings.model.SwitchableSetting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    private val settingsListBuilder: SettingsListBuilder
) : ViewModel() {

    private val _settingsState: MutableStateFlow<SettingsState> =
        MutableStateFlow(SettingsState(items = settingsListBuilder.build()))
    val settingsState: StateFlow<SettingsState> = _settingsState

    fun onSettingClick(type: ClickableSetting) {
        // TODO: Implement
    }

    fun onSettingSwitch(type: SwitchableSetting, isEnabled: Boolean) {
        // TODO: Implement
    }
}
