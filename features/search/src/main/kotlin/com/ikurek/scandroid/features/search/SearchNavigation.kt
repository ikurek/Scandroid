package com.ikurek.scandroid.features.search

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ikurek.scandroid.features.search.ui.SearchScreen
import com.ikurek.scandroid.features.search.ui.SearchViewModel

const val SearchRoute = "search"

fun NavGraphBuilder.searchScreen() {
    composable(route = SearchRoute) {
        val viewModel: SearchViewModel = hiltViewModel()

        SearchScreen()
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
