package com.ikurek.scandroid.features.search

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ikurek.scandroid.features.search.ui.SearchScreen
import com.ikurek.scandroid.features.search.ui.SearchViewModel
import java.util.UUID

const val SearchRoute = "search"

fun NavGraphBuilder.searchScreen(onScanClick: (id: UUID) -> Unit) {
    composable(route = SearchRoute) {
        val viewModel: SearchViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()

        SearchScreen(
            state = state,
            onQueryChange = viewModel::onQueryChanged,
            onScanClick = onScanClick
        )
    }
}

fun NavController.navigateToSearch() {
    navigate(SearchRoute) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
