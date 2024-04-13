package com.ikurek.scandroid.features.scandetails.ui.scandetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikurek.scandroid.features.savedscans.usecase.GetSavedScan
import com.ikurek.scandroid.features.scandetails.ScanDetailsScreenArgs
import com.ikurek.scandroid.features.scandetails.ui.scandetails.model.SavedScanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ScanDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSavedScan: GetSavedScan
) : ViewModel() {

    private val args: ScanDetailsScreenArgs = ScanDetailsScreenArgs(savedStateHandle)

    private val _scanState: MutableStateFlow<SavedScanState> = MutableStateFlow(SavedScanState.Loading)
    val scanState: StateFlow<SavedScanState> = _scanState

    fun onScreenEnter() = viewModelScope.launch {
        getSavedScan(args.scanId).onSuccess {
            _scanState.value = SavedScanState.Loaded(it)
        }.onFailure {
            _scanState.value = SavedScanState.Error
        }
    }
}
