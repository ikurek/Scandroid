package com.ikurek.scandroid.features.savedscans

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikurek.scandroid.features.createscan.usecase.DeleteLatestUnsavedScan
import com.ikurek.scandroid.features.createscan.usecase.GetLatestUnsavedScan
import com.ikurek.scandroid.features.savedscans.model.SavedScansState
import com.ikurek.scandroid.features.savedscans.model.UnsavedScanState
import com.ikurek.scandroid.features.savedscans.usecase.GetAllValidSavedScans
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SavedScansViewModel @Inject constructor(
    private val getLatestUnsavedScan: GetLatestUnsavedScan,
    private val getAllValidSavedScans: GetAllValidSavedScans,
    private val deleteLatestUnsavedScan: DeleteLatestUnsavedScan
) : ViewModel() {

    private val _unsavedScanState: MutableStateFlow<UnsavedScanState> =
        MutableStateFlow(UnsavedScanState.Empty)
    val unsavedScanState: StateFlow<UnsavedScanState> = _unsavedScanState

    private val _scansState: MutableStateFlow<SavedScansState> =
        MutableStateFlow(SavedScansState.Loading)
    val scansState: StateFlow<SavedScansState> = _scansState

    fun onScreenEnter() = viewModelScope.launch {
        refreshUnsavedScan()
        refreshSavedScans()
    }

    private fun refreshUnsavedScan() {
        if (getLatestUnsavedScan() != null) {
            _unsavedScanState.value = UnsavedScanState.Present
        } else {
            _unsavedScanState.value = UnsavedScanState.Empty
        }
    }

    private suspend fun refreshSavedScans() {
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

    fun deleteUnsavedScan() {
        deleteLatestUnsavedScan()
        _unsavedScanState.value = UnsavedScanState.Empty
    }
}
