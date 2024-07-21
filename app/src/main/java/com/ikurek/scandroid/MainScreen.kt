package com.ikurek.scandroid

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ikurek.scandroid.core.design.components.dialogs.ErrorDialog
import com.ikurek.scandroid.feature.home.HomeRoute
import com.ikurek.scandroid.feature.home.homeScreen
import com.ikurek.scandroid.features.createscan.data.model.ScannedDocuments
import com.ikurek.scandroid.features.createscan.navigateToNewScan
import com.ikurek.scandroid.features.createscan.newScanScreen
import com.ikurek.scandroid.features.scandetails.navigateToScanDetails
import com.ikurek.scandroid.features.scandetails.navigateToScanImageGallery
import com.ikurek.scandroid.features.scandetails.scanDetailsScreen
import com.ikurek.scandroid.features.scandetails.scanImageGalleryScreen
import com.ikurek.scandroid.features.settings.ui.aboutScreen
import com.ikurek.scandroid.features.settings.ui.navigateToAbout
import com.ikurek.scandroid.features.settings.ui.navigateToSettings
import com.ikurek.scandroid.features.settings.ui.settingsScreen

@Suppress("LongMethod")
@Composable
fun MainScreen(
    createScanRequest: suspend () -> Result<IntentSenderRequest>,
    parseScanResult: (ActivityResult) -> Result<ScannedDocuments>
) {
    val mainNavController: NavHostController = rememberNavController()
    val mainViewModel: MainViewModel = hiltViewModel()
    val scannerLauncher = rememberLauncherForActivityResult(StartIntentSenderForResult()) {
        mainViewModel.onScannerFinished(parseScanResult(it))
    }
    val dialog: MainDialog? by mainViewModel.dialog.collectAsState()

    // It's hacky but I had to navigate from both scanner callback and viewmodel and it was
    // the fastest solution that could get me both
    LaunchedEffect(mainViewModel.sideEffects) {
        mainViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is MainSideEffect.StartDocumentScanner -> {
                    createScanRequest().onSuccess {
                        scannerLauncher.launch(it)
                    }.onFailure {
                        mainViewModel.onScannerInitializationFailed(it)
                    }
                }

                is MainSideEffect.OpenNewScanScreen -> mainNavController.navigateToNewScan()
            }
        }
    }

    LaunchedEffect(mainNavController) {
        mainNavController.addOnDestinationChangedListener { _, destination, _ ->
            mainViewModel.onDestinationChanged(destination.route!!)
        }
    }

    dialog?.let {
        MainScreenDialog(
            mainDialog = it,
            onDismissRequest = mainViewModel::onDialogDismissed,
        )
    }

    NavHost(
        navController = mainNavController,
        startDestination = HomeRoute,
        modifier = Modifier.fillMaxSize(),
        enterTransition = { slideIntoContainer(towards = SlideDirection.Start) },
        exitTransition = { slideOutOfContainer(towards = SlideDirection.Start) },
        popEnterTransition = { slideIntoContainer(towards = SlideDirection.End) },
        popExitTransition = { slideOutOfContainer(towards = SlideDirection.End) }
    ) {
        homeScreen(
            onSettingsClick = { mainNavController.navigateToSettings() },
            onRestoreUnsavedScanClick = { mainNavController.navigateToNewScan() },
            onScanClick = { scanId -> mainNavController.navigateToScanDetails(scanId) },
            onCreateScanClick = mainViewModel::onCreateScanClick
        )
        newScanScreen(
            onScanCreated = { scanId ->
                mainNavController.navigateToScanDetails(scanId) {
                    popUpTo(HomeRoute)
                }
            },
            onNavigateUp = mainNavController::navigateUp
        )
        scanDetailsScreen(
            onImageClick = { scanId, imageIndex ->
                mainNavController.navigateToScanImageGallery(scanId, imageIndex)
            },
            onScanDeleted = mainNavController::navigateUp,
            onNavigateUp = mainNavController::navigateUp
        )
        scanImageGalleryScreen(
            onNavigateUp = mainNavController::navigateUp
        )
        settingsScreen(
            onAboutClick = { mainNavController.navigateToAbout() },
            onNavigateUp = mainNavController::navigateUp
        )
        aboutScreen(
            onNavigateUp = mainNavController::navigateUp
        )
    }
}

@Composable
private fun MainScreenDialog(
    mainDialog: MainDialog,
    onDismissRequest: () -> Unit,
) {
    when (mainDialog) {
        is MainDialog.Error -> ErrorDialog(
            title = stringResource(id = mainDialog.titleRes),
            content = stringResource(id = mainDialog.contentRes),
            exception = mainDialog.exception,
            onDismissRequest = onDismissRequest
        )
    }
}
