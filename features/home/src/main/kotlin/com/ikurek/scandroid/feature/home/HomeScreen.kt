package com.ikurek.scandroid.feature.home

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ikurek.scandroid.features.createscan.model.ScannedDocuments
import com.ikurek.scandroid.features.createscan.model.ScannerSettings
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
                }
            }
        }
    }

    NavHost(
        navController = homeNavController,
        startDestination = SavedScansRoute,
        modifier = Modifier.fillMaxSize()
    ) {
        savedScansScreen(onCreateScanClick = homeViewModel::onCreateScanClick)
    }
}
