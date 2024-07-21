package com.ikurek.scandroid.feature.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ikurek.scandroid.features.savedscans.SavedScansRoute
import com.ikurek.scandroid.features.savedscans.savedScansScreen
import java.util.UUID

@Suppress("LongMethod")
@Composable
internal fun HomeScreen(
    onSettingsClick: () -> Unit,
    onRestoreUnsavedScanClick: () -> Unit,
    onScanClick: (scanId: UUID) -> Unit,
    onCreateScanClick: () -> Unit,
    onHomeDestinationChange: (route: String) -> Unit
) {
    val homeNavController: NavHostController = rememberNavController()

    LaunchedEffect(homeNavController) {
        homeNavController.addOnDestinationChangedListener { _, destination, _ ->
            onHomeDestinationChange(destination.route!!)
        }
    }

    NavHost(
        navController = homeNavController,
        startDestination = SavedScansRoute,
        modifier = Modifier.fillMaxSize(),
    ) {
        savedScansScreen(
            onSettingsClick = onSettingsClick,
            onRestoreUnsavedScanClick = onRestoreUnsavedScanClick,
            onScanClick = onScanClick,
            onCreateScanClick = onCreateScanClick
        )
    }
}
