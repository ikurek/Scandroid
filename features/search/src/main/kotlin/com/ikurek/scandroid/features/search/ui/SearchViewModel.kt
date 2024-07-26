package com.ikurek.scandroid.features.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikurek.scandroid.features.search.usecase.SearchScans
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val searchScans: SearchScans,
    private val searchListBuilder: SearchListBuilder
) : ViewModel() {

    private val inputQuery: MutableStateFlow<String> = MutableStateFlow("")

    val state: StateFlow<SearchState> = inputQuery.map(::searchScansForQuery)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SearchState.NotSearching)

    private suspend fun searchScansForQuery(query: String): SearchState = if (query.isBlank()) {
        SearchState.NotSearching
    } else {
        val scans = searchScans(query)
        if (scans.isEmpty()) {
            SearchState.NothingFound(query)
        } else {
            SearchState.Found(query, searchListBuilder.build(scans))
        }
    }

    fun onQueryChanged(query: String) {
        inputQuery.value = query
    }
}
