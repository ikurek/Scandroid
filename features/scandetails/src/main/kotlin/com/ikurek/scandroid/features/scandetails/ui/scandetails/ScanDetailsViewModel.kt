package com.ikurek.scandroid.features.scandetails.ui.scandetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ikurek.scandroid.features.scandetails.ScanDetailsScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
internal class ScanDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args: ScanDetailsScreenArgs = ScanDetailsScreenArgs(savedStateHandle)

    val scanId: StateFlow<String> = MutableStateFlow(args.scanId)
}
