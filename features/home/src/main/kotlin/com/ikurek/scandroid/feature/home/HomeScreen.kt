package com.ikurek.scandroid.feature.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ikurek.scandroid.features.savedscans.SavedScansRoute
import com.ikurek.scandroid.features.savedscans.savedScansScreen

@Composable
fun HomeScreen() {
    val homeNavController = rememberNavController()

    NavHost(
        navController = homeNavController,
        startDestination = SavedScansRoute,
        modifier = Modifier.fillMaxSize()
    ) {
        savedScansScreen()
    }
}
