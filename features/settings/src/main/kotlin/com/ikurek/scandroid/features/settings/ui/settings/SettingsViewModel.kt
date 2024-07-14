package com.ikurek.scandroid.features.settings.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikurek.scandroid.features.settings.data.model.ScannerFormats
import com.ikurek.scandroid.features.settings.data.model.ScannerMode
import com.ikurek.scandroid.features.settings.ui.settings.builder.SettingsDialogBuilder
import com.ikurek.scandroid.features.settings.ui.settings.builder.SettingsListBuilder
import com.ikurek.scandroid.features.settings.ui.settings.model.ClickableSetting
import com.ikurek.scandroid.features.settings.ui.settings.model.SettingsState
import com.ikurek.scandroid.features.settings.ui.settings.model.SwitchableSetting
import com.ikurek.scandroid.features.settings.usecase.OpenAppInPlayStore
import com.ikurek.scandroid.features.settings.usecase.SetAnalyticsEnabled
import com.ikurek.scandroid.features.settings.usecase.SetCrashlyticsEnabled
import com.ikurek.scandroid.features.settings.usecase.SetPerformanceMonitoringEnabled
import com.ikurek.scandroid.features.settings.usecase.SetScannerFormats
import com.ikurek.scandroid.features.settings.usecase.SetScannerMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("LongParameterList")
@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    private val settingsListBuilder: SettingsListBuilder,
    private val settingsDialogBuilder: SettingsDialogBuilder,
    private val setAnalyticsEnabled: SetAnalyticsEnabled,
    private val setCrashlyticsEnabled: SetCrashlyticsEnabled,
    private val setPerformanceMonitoringEnabled: SetPerformanceMonitoringEnabled,
    private val openAppInPlayStore: OpenAppInPlayStore,
    private val setScannerMode: SetScannerMode,
    private val setScannerFormats: SetScannerFormats
) : ViewModel() {

    private val _dialog: MutableStateFlow<SettingsDialog?> = MutableStateFlow(null)
    val dialog: StateFlow<SettingsDialog?> = _dialog

    private val _sideEffects: Channel<SettingsSideEffect> = Channel()
    val sideEffects: Flow<SettingsSideEffect> = _sideEffects.receiveAsFlow()

    private val _settingsState: MutableStateFlow<SettingsState> =
        MutableStateFlow(SettingsState(items = emptyList()))
    val settingsState: StateFlow<SettingsState> = _settingsState

    init {
        viewModelScope.launch {
            _settingsState.update { it.copy(items = settingsListBuilder.build()) }
        }
    }

    fun onSettingClick(type: ClickableSetting) = viewModelScope.launch {
        when (type) {
            ClickableSetting.ScannerMode ->
                _dialog.value = settingsDialogBuilder.buildScannerModeSelectionDialog()

            ClickableSetting.ScannerFileFormats ->
                _dialog.value = settingsDialogBuilder.buildScannerFormatsSelectionDialog()

            ClickableSetting.About ->
                _sideEffects.send(SettingsSideEffect.AboutClicked)

            ClickableSetting.RateApp -> openAppInPlayStore()
        }
    }

    fun onSettingSwitch(type: SwitchableSetting, isEnabled: Boolean) = viewModelScope.launch {
        when (type) {
            SwitchableSetting.AnalyticsEnabled -> setAnalyticsEnabled(isEnabled)
            SwitchableSetting.CrashlyticsEnabled -> setCrashlyticsEnabled(isEnabled)
            SwitchableSetting.PerformanceMonitoringEnabled -> setPerformanceMonitoringEnabled(
                isEnabled
            )
        }
        _settingsState.update { it.copy(items = settingsListBuilder.build()) }
    }

    fun onSaveScannerMode(scannerMode: ScannerMode) = viewModelScope.launch {
        setScannerMode(scannerMode)
        _dialog.value = null
    }

    fun onSaveScannerFormats(scannerFormats: ScannerFormats) = viewModelScope.launch {
        setScannerFormats(scannerFormats)
        _dialog.value = null
    }

    fun onDialogDismiss() {
        _dialog.value = null
    }
}
