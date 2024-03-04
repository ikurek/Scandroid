package com.ikurek.scandroid.features.createscan

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ikurek.scandroid.features.createscan.ui.newscan.NewScanScreen
import com.ikurek.scandroid.features.createscan.ui.newscan.NewScanViewModel

const val NewScanRoute = "new-scan"

@Suppress("UnusedPrivateProperty")
fun NavGraphBuilder.newScanScreen() {
    composable(route = NewScanRoute) {
        val viewModel: NewScanViewModel = hiltViewModel()

        NewScanScreen()
    }
}

fun NavController.navigateToNewScanScreen() {
    navigate(NewScanRoute)
}
