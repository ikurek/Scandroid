package com.ikurek.scandroid.features.settings.ui

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ikurek.scandroid.features.settings.ui.about.AboutScreen
import com.ikurek.scandroid.features.settings.ui.about.AboutState
import com.ikurek.scandroid.features.settings.ui.about.AboutViewModel
import com.ikurek.scandroid.features.settings.ui.settings.SettingsDialog
import com.ikurek.scandroid.features.settings.ui.settings.SettingsScreen
import com.ikurek.scandroid.features.settings.ui.settings.SettingsSideEffect
import com.ikurek.scandroid.features.settings.ui.settings.SettingsViewModel
import com.ikurek.scandroid.features.settings.ui.settings.model.SettingsState

internal const val AboutRoute = "about"
internal const val SettingsRoute = "settings"

fun NavGraphBuilder.settingsScreen(
    onAboutClick: () -> Unit,
    onNavigateUp: () -> Unit
) {
    composable(route = SettingsRoute) {
        val viewModel: SettingsViewModel = hiltViewModel()
        val settingsState: SettingsState by viewModel.settingsState.collectAsState()
        val dialog: SettingsDialog? by viewModel.dialog.collectAsState()

        LaunchedEffect(viewModel.sideEffects) {
            viewModel.sideEffects.collect { sideEffect ->
                when (sideEffect) {
                    is SettingsSideEffect.AboutClicked -> onAboutClick()
                }
            }
        }

        SettingsScreen(
            settingsState = settingsState,
            dialog = dialog,
            onSettingClick = viewModel::onSettingClick,
            onSettingSwitch = viewModel::onSettingSwitch,
            onSaveScannerMode = viewModel::onSaveScannerMode,
            onSaveScannerFormats = viewModel::onSaveScannerFormats,
            onDialogDismiss = viewModel::onDialogDismiss,
            onNavigateUp = onNavigateUp
        )
    }
}

fun NavGraphBuilder.aboutScreen(onNavigateUp: () -> Unit) {
    composable(route = AboutRoute) {
        val viewModel: AboutViewModel = hiltViewModel()
        val aboutState: AboutState by viewModel.state.collectAsState()

        AboutScreen(
            state = aboutState,
            onNavigateUp = onNavigateUp
        )
    }
}

fun NavController.navigateToSettings() {
    navigate(SettingsRoute)
}

fun NavController.navigateToAbout() {
    navigate(AboutRoute)
}
