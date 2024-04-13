package com.ikurek.scandroid.features.savedscans

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikurek.scandroid.features.savedscans.model.SavedScansState
import com.ikurek.scandroid.features.savedscans.usecase.GetAllValidSavedScans
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SavedScansViewModel @Inject constructor(
    private val getAllValidSavedScans: GetAllValidSavedScans
) : ViewModel() {

    private val _scansState: MutableStateFlow<SavedScansState> =
        MutableStateFlow(SavedScansState.Loading)
    val scansState: StateFlow<SavedScansState> = _scansState

    fun onScreenEnter() = viewModelScope.launch {
        getAllValidSavedScans().onSuccess { scans ->
            if (scans.isEmpty()) {
                _scansState.value = SavedScansState.Empty
            } else {
                _scansState.value = SavedScansState.Loaded(scans)
            }
        }.onFailure {
            _scansState.value = SavedScansState.Error
        }
    }
}
