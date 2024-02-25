package com.ikurek.scandroid.features.savedscans

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val SavedScansRoute = "saved-scans"

fun NavGraphBuilder.savedScansScreen(onCreateScanClick: () -> Unit) {
    composable(route = SavedScansRoute) {
        val viewModel: SavedScansViewModel = hiltViewModel()
        val scans: List<String> by viewModel.scans.collectAsState()

        SavedScansScreen(
            scans = scans,
            onCreateScanClick = onCreateScanClick
        )
    }
}
