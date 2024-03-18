package com.ikurek.scandroid.features.savedscans

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
internal class SavedScansViewModel @Inject constructor() : ViewModel() {

    // TODO: Implement
    val scans: StateFlow<List<String>> = MutableStateFlow(emptyList())
}
