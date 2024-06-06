package com.ikurek.scandroid.features.scandetails.ui.scandetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikurek.scandroid.features.savedscans.data.model.SavedScanFiles
import com.ikurek.scandroid.features.savedscans.usecase.GetSavedScan
import com.ikurek.scandroid.features.scandetails.ScanDetailsScreenArgs
import com.ikurek.scandroid.features.scandetails.ui.scandetails.model.PdfAndImagesTabs
import com.ikurek.scandroid.features.scandetails.ui.scandetails.model.SavedScanState
import com.ikurek.scandroid.features.scandetails.ui.scandetails.model.ScanAction
import com.ikurek.scandroid.features.scandetails.ui.scandetails.model.availableScanActions
import com.ikurek.scandroid.features.scandetails.usecase.OpenPdfFile
import com.ikurek.scandroid.features.scandetails.usecase.SelectedFileType
import com.ikurek.scandroid.features.scandetails.usecase.ShareScanFiles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ScanDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSavedScan: GetSavedScan,
    private val openPdfFile: OpenPdfFile,
    private val shareScanFiles: ShareScanFiles
) : ViewModel() {

    private val args: ScanDetailsScreenArgs = ScanDetailsScreenArgs(savedStateHandle)

    private val _scanState: MutableStateFlow<SavedScanState> =
        MutableStateFlow(SavedScanState.Loading)
    val scanState: StateFlow<SavedScanState> = _scanState

    private val _availableScanActions: MutableStateFlow<List<ScanAction>> =
        MutableStateFlow(emptyList())
    val availableScanActions: StateFlow<List<ScanAction>> = _availableScanActions

    private var currentPage: PdfAndImagesTabs? = null

    fun onScreenEnter() = viewModelScope.launch {
        getSavedScan(args.scanId).onSuccess {
            _scanState.value = SavedScanState.Loaded(it)
            _availableScanActions.value = scanState.value.availableScanActions
        }.onFailure {
            _scanState.value = SavedScanState.Error
        }
    }

    fun onOpenPdfOutsideClick(savedScanFiles: SavedScanFiles) = viewModelScope.launch {
        checkNotNull(savedScanFiles.pdfFile) { "No PDF file to open" }
        openPdfFile(savedScanFiles.pdfFile!!)
    }

    fun onShareFilesClick(savedScanFiles: SavedScanFiles) = viewModelScope.launch {
        shareScanFiles(
            savedScanFiles = savedScanFiles,
            selectedFileType = when (currentPage) {
                PdfAndImagesTabs.Images -> SelectedFileType.Images
                PdfAndImagesTabs.PDF -> SelectedFileType.PDF
                else -> null
            }
        )
    }

    fun onFileTypePageChange(newPage: PdfAndImagesTabs) = viewModelScope.launch {
        currentPage = newPage
        _availableScanActions.value = when (newPage) {
            PdfAndImagesTabs.PDF -> listOf(ScanAction.Share, ScanAction.OpenPdfOutside)
            PdfAndImagesTabs.Images -> listOf(ScanAction.Share)
        }
    }
}
