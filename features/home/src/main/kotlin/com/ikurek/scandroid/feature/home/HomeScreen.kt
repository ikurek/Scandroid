package com.ikurek.scandroid.feature.home

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ikurek.scandroid.core.design.components.dialogs.ErrorDialog
import com.ikurek.scandroid.features.createscan.data.model.ScannedDocuments
import com.ikurek.scandroid.features.createscan.data.model.ScannerSettings
import com.ikurek.scandroid.features.createscan.navigateToNewScan
import com.ikurek.scandroid.features.createscan.newScanScreen
import com.ikurek.scandroid.features.savedscans.SavedScansRoute
import com.ikurek.scandroid.features.savedscans.savedScansScreen
import com.ikurek.scandroid.features.scandetails.navigateToScanDetails
import com.ikurek.scandroid.features.scandetails.scanDetailsScreen

@Composable
fun HomeScreen(
    createScanRequest: suspend (ScannerSettings) -> Result<IntentSenderRequest>,
    parseScanResult: (ActivityResult) -> Result<ScannedDocuments>
) {
    val homeNavController: NavHostController = rememberNavController()
    val homeViewModel: HomeViewModel = hiltViewModel()
    val lifecycleOwner = LocalLifecycleOwner.current
    val scannerLauncher = rememberLauncherForActivityResult(StartIntentSenderForResult()) {
        homeViewModel.onScannerFinished(parseScanResult(it))
    }
    val dialog: HomeDialog? by homeViewModel.dialog.collectAsState()

    // It's hacky but I had to navigate from both scanner callback and viewmodel and it was
    // the fastest solution that could get me both
    LaunchedEffect(homeViewModel.sideEffects) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            homeViewModel.sideEffects.collect { sideEffect ->
                when (sideEffect) {
                    is HomeSideEffect.StartDocumentScanner -> {
                        createScanRequest(sideEffect.settings).onSuccess {
                            scannerLauncher.launch(it)
                        }.onFailure {
                            homeViewModel.onScannerInitializationFailed(it)
                        }
                    }

                    is HomeSideEffect.OpenNewScanScreen -> homeNavController.navigateToNewScan()
                }
            }
        }
    }

    dialog?.let {
        HomeScreenDialog(
            homeDialog = it,
            onDismissRequest = homeViewModel::onDialogDismissed,
        )
    }

    NavHost(
        navController = homeNavController,
        startDestination = SavedScansRoute,
        modifier = Modifier.fillMaxSize()
    ) {
        savedScansScreen(
            onScanClick = { scanId -> homeNavController.navigateToScanDetails(scanId) },
            onCreateScanClick = homeViewModel::onCreateScanClick
        )
        newScanScreen(
            onScanCreated = { scanId ->
                homeNavController.navigateToScanDetails(scanId) {
                    popUpTo(SavedScansRoute)
                }
            }
        )
        scanDetailsScreen()
    }
}

@Composable
private fun HomeScreenDialog(
    homeDialog: HomeDialog,
    onDismissRequest: () -> Unit,
) {
    when (homeDialog) {
        is HomeDialog.Error -> ErrorDialog(
            title = stringResource(id = homeDialog.titleRes),
            content = stringResource(id = homeDialog.contentRes),
            exception = homeDialog.exception,
            onDismissRequest = onDismissRequest
        )
    }
}
