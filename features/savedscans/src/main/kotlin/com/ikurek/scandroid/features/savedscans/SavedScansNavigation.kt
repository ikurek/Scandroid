package com.ikurek.scandroid.features.savedscans

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ikurek.scandroid.features.savedscans.model.SavedScansState
import java.util.UUID

const val SavedScansRoute = "saved-scans"

fun NavGraphBuilder.savedScansScreen(
    onScanClick: (scanId: UUID) -> Unit,
    onCreateScanClick: () -> Unit
) {
    composable(route = SavedScansRoute) {
        val viewModel: SavedScansViewModel = hiltViewModel()
        val scansState: SavedScansState by viewModel.scansState.collectAsState()

        LaunchedEffect(Unit) { viewModel.onScreenEnter() }

        SavedScansScreen(
            scansState = scansState,
            onScanClick = onScanClick,
            onCreateScanClick = onCreateScanClick
        )
    }
}
