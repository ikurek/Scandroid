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
import com.ikurek.scandroid.core.design.components.dialogs.InfoDialog
import com.ikurek.scandroid.features.createscan.model.ScannedDocuments
import com.ikurek.scandroid.features.createscan.model.ScannerSettings
import com.ikurek.scandroid.features.createscan.navigateToNewScanScreen
import com.ikurek.scandroid.features.createscan.newScanScreen
import com.ikurek.scandroid.features.savedscans.SavedScansRoute
import com.ikurek.scandroid.features.savedscans.savedScansScreen

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
    val infoDialog: HomeInfoDialog? by homeViewModel.infoDialog.collectAsState()

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

                    is HomeSideEffect.OpenNewScanScreen -> homeNavController.navigateToNewScanScreen()
                }
            }
        }
    }

    if (infoDialog != null) {
        HomeScreenDialog(
            homeInfoDialog = infoDialog!!,
            onDismissRequest = homeViewModel::onInfoDialogDismissed,
        )
    }

    NavHost(
        navController = homeNavController,
        startDestination = SavedScansRoute,
        modifier = Modifier.fillMaxSize()
    ) {
        savedScansScreen(onCreateScanClick = homeViewModel::onCreateScanClick)
        newScanScreen()
    }
}

@Composable
private fun HomeScreenDialog(
    homeInfoDialog: HomeInfoDialog,
    onDismissRequest: () -> Unit,
) {
    if (homeInfoDialog.isError) {
        ErrorDialog(
            title = stringResource(id = homeInfoDialog.titleRes),
            content = stringResource(id = homeInfoDialog.contentRes),
            onDismissRequest = onDismissRequest
        )
    } else {
        InfoDialog(
            title = stringResource(id = homeInfoDialog.titleRes),
            content = stringResource(id = homeInfoDialog.contentRes),
            onDismissRequest = onDismissRequest
        )
    }
}
