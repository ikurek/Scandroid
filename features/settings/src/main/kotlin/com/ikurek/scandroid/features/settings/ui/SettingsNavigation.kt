package com.ikurek.scandroid.features.settings.ui

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ikurek.scandroid.features.settings.ui.settings.SettingsScreen
import com.ikurek.scandroid.features.settings.ui.settings.SettingsViewModel
import com.ikurek.scandroid.features.settings.ui.settings.model.SettingsState

internal const val SettingsRoute = "settings"

fun NavGraphBuilder.settingsScreen(onNavigateUp: () -> Unit) {
    composable(route = SettingsRoute) {
        val viewModel: SettingsViewModel = hiltViewModel()
        val settingsState: SettingsState by viewModel.settingsState.collectAsState()

        SettingsScreen(
            settingsState = settingsState,
            onSettingClick = viewModel::onSettingClick,
            onSettingSwitch = viewModel::onSettingSwitch,
            onNavigateUp = onNavigateUp
        )
    }
}

fun NavController.navigateToSettings() {
    navigate(SettingsRoute)
}
