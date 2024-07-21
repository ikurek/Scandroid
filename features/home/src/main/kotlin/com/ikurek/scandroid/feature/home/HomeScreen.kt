package com.ikurek.scandroid.feature.home

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.core.design.components.appbar.PrimaryTopAppBar
import com.ikurek.scandroid.core.translations.R
import com.ikurek.scandroid.features.savedscans.SavedScansRoute
import com.ikurek.scandroid.features.savedscans.navigateToSavedScans
import com.ikurek.scandroid.features.savedscans.savedScansScreen
import com.ikurek.scandroid.features.search.SearchRoute
import com.ikurek.scandroid.features.search.navigateToSearch
import com.ikurek.scandroid.features.search.searchScreen
import java.util.UUID
import com.ikurek.scandroid.core.translations.R as TranslationsR

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

    Scaffold(
        topBar = { AppBarWithSettingsAction(onSettingsClick) },
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
                ScansNavigationBarItem(
                    isSelected = navBackStackEntry?.destination?.route == SavedScansRoute,
                    onClick = homeNavController::navigateToSavedScans
                )
                SearchNavigationBarItem(
                    isSelected = navBackStackEntry?.destination?.route == SearchRoute,
                    onClick = homeNavController::navigateToSearch
                )
            }
        }
    ) { contentPadding ->
        NavHost(
            navController = homeNavController,
            startDestination = SavedScansRoute,
            modifier = Modifier.padding(contentPadding),
        ) {
            savedScansScreen(
                onRestoreUnsavedScanClick = onRestoreUnsavedScanClick,
                onScanClick = onScanClick,
                onCreateScanClick = onCreateScanClick
            )
            searchScreen()
        }
    }
}

@Composable
private fun AppBarWithSettingsAction(onSettingsClick: () -> Unit) {
    PrimaryTopAppBar(
        title = stringResource(R.string.app_name),
        actions = {
            IconButton(onSettingsClick) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(
                        id = R.string.saved_scans_settings_icon_content_descriptions
                    )
                )
            }
        }
    )
}

@Composable
private fun RowScope.ScansNavigationBarItem(isSelected: Boolean, onClick: () -> Unit) {
    NavigationBarItem(
        selected = isSelected,
        icon = {
            Icon(
                Icons.AutoMirrored.Default.List,
                contentDescription = stringResource(
                    id = TranslationsR.string.home_bottom_nav_saved_scans_icon_content_description
                )
            )
        },
        label = {
            Text(text = stringResource(TranslationsR.string.home_bottom_nav_saved_scans_label))
        },
        onClick = onClick
    )
}

@Composable
private fun RowScope.SearchNavigationBarItem(isSelected: Boolean, onClick: () -> Unit) {
    NavigationBarItem(
        selected = isSelected,
        icon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(
                    id = TranslationsR.string.home_bottom_nav_search_scans_icon_content_description
                )
            )
        },
        label = {
            Text(text = stringResource(TranslationsR.string.home_bottom_nav_search_scans_label))
        },
        onClick = onClick
    )
}

@PreviewLightDark
@Composable
private fun Preview() {
    ScandroidTheme {
        HomeScreen(
            onSettingsClick = {},
            onRestoreUnsavedScanClick = {},
            onScanClick = {},
            onCreateScanClick = {},
            onHomeDestinationChange = {}
        )
    }
}
