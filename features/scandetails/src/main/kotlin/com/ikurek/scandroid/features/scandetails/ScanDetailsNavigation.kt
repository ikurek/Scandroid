package com.ikurek.scandroid.features.scandetails

import androidx.compose.runtime.LaunchedEffect
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
import com.ikurek.scandroid.features.scandetails.ui.scandetails.model.SavedScanState
import com.ikurek.scandroid.features.scandetails.ui.scanimagegallery.ScanImageGalleryScreen
import com.ikurek.scandroid.features.scandetails.ui.scanimagegallery.ScanImageGalleryViewModel
import java.util.UUID

private const val ScanId = "scanId"
private const val InitialImageIndex = "initialImageIndex"
internal const val ScanDetailsRoute = "scan-details/{$ScanId}"
internal const val ScanImageGalleryRoute = "scan-details/{$ScanId}/images/{$InitialImageIndex}"

internal data class ScanDetailsScreenArgs(val scanId: UUID) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        scanId = savedStateHandle.get<String>(ScanId)?.let { UUID.fromString(it) }
            ?: error("$ScanId argument not present")
    )
}

fun NavGraphBuilder.scanDetailsScreen(
    onImageClick: (scanId: UUID, imageIndex: Int) -> Unit,
    onNavigateUp: () -> Unit
) {
    composable(route = ScanDetailsRoute) {
        val viewModel: ScanDetailsViewModel = hiltViewModel()
        val scanState: SavedScanState by viewModel.scanState.collectAsState()

        LaunchedEffect(Unit) { viewModel.onScreenEnter() }

        ScanDetailsScreen(
            scanState = scanState,
            onImageClick = onImageClick,
            onNavigateUp = onNavigateUp
        )
    }
}

fun NavController.navigateToScanDetails(scanId: UUID, builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(ScanDetailsRoute.replace("{$ScanId}", scanId.toString()), builder)
}

internal data class ScanImageGalleryScreenArgs(val scanId: UUID, val initialImageIndex: Int) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        scanId = savedStateHandle.get<String>(ScanId)?.let { UUID.fromString(it) }
            ?: error("$ScanId argument not present"),
        initialImageIndex = savedStateHandle.get<String>(InitialImageIndex)?.toIntOrNull()
            ?: error("$InitialImageIndex argument not present")
    )
}

fun NavGraphBuilder.scanImageGalleryScreen(
    onNavigateUp: () -> Unit
) {
    composable(route = ScanImageGalleryRoute) {
        val viewModel: ScanImageGalleryViewModel = hiltViewModel()
        val imageGalleryState by viewModel.imageGalleryState.collectAsState()
        val currentImageIndex by viewModel.currentImageIndex.collectAsState()

        LaunchedEffect(Unit) { viewModel.onScreenEnter() }

        ScanImageGalleryScreen(
            imageGalleryState = imageGalleryState,
            currentImageIndex = currentImageIndex,
            onImageChange = viewModel::onImageChange,
            onNavigateUp = onNavigateUp
        )
    }
}

fun NavController.navigateToScanImageGallery(
    scanId: UUID,
    imageIndex: Int,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    navigate(
        ScanImageGalleryRoute
            .replace("{$ScanId}", scanId.toString())
            .replace("{$InitialImageIndex}", imageIndex.toString()),
        builder
    )
}
