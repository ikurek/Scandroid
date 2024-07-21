package com.ikurek.scandroid.features.savedscans

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ikurek.scandroid.features.savedscans.model.SavedScansState
import com.ikurek.scandroid.features.savedscans.model.UnsavedScanState
import java.util.UUID

const val SavedScansRoute = "saved-scans"

fun NavGraphBuilder.savedScansScreen(
    onRestoreUnsavedScanClick: () -> Unit,
    onScanClick: (scanId: UUID) -> Unit,
    onCreateScanClick: () -> Unit
) {
    composable(route = SavedScansRoute) {
        val viewModel: SavedScansViewModel = hiltViewModel()
        val unsavedScanState: UnsavedScanState by viewModel.unsavedScanState.collectAsState()
        val selectedSortingMode by viewModel.selectedSortingMode.collectAsState()
        val scansState: SavedScansState by viewModel.scansState.collectAsState()

        LaunchedEffect(Unit) { viewModel.onScreenEnter() }

        SavedScansScreen(
            unsavedScanState = unsavedScanState,
            selectedSortingMode = selectedSortingMode,
            scansState = scansState,
            onRestoreUnsavedScanClick = onRestoreUnsavedScanClick,
            onDeleteUnsavedScanClick = viewModel::deleteUnsavedScan,
            onSortingModeClick = viewModel::onSortingModeClick,
            onScanClick = onScanClick,
            onCreateScanClick = onCreateScanClick
        )
    }
}

fun NavController.navigateToSavedScans() {
    navigate(SavedScansRoute) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
