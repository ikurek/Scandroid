package com.ikurek.scandroid.features.scandetails

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.ikurek.scandroid.features.scandetails.ui.scandetails.ScanDetailsScreen
import com.ikurek.scandroid.features.scandetails.ui.scandetails.ScanDetailsViewModel
import java.util.UUID

private const val ScanId = "scanId"
internal const val ScanDetailsRoute = "scan-details/{$ScanId}"

internal data class ScanDetailsScreenArgs(val scanId: String) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        scanId = savedStateHandle[ScanId] ?: error("$ScanId argument not present")
    )
}

fun NavGraphBuilder.scanDetailsScreen() {
    composable(route = ScanDetailsRoute) {
        val viewModel: ScanDetailsViewModel = hiltViewModel()
        val scanId: String by viewModel.scanId.collectAsState()

        ScanDetailsScreen(scanId = scanId)
    }
}

fun NavController.navigateToScanDetails(scanId: UUID, builder: NavOptionsBuilder.() -> Unit) {
    navigate(ScanDetailsRoute.replace("{$ScanId}", scanId.toString()), builder)
}
