package com.ikurek.scandroid.feature.home

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import java.util.UUID

const val HomeRoute = "home"

fun NavGraphBuilder.homeScreen(
    onSettingsClick: () -> Unit,
    onRestoreUnsavedScanClick: () -> Unit,
    onScanClick: (scanId: UUID) -> Unit,
    onCreateScanClick: () -> Unit
) {
    composable(route = HomeRoute) {
        val viewModel: HomeViewModel = hiltViewModel()

        HomeScreen(
            onSettingsClick = onSettingsClick,
            onRestoreUnsavedScanClick = onRestoreUnsavedScanClick,
            onScanClick = onScanClick,
            onCreateScanClick = onCreateScanClick,
            onHomeDestinationChange = viewModel::onDestinationChanged
        )
    }
}
