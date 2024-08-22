package com.ikurek.scandroid.features.scandetails.ui.scandetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikurek.scandroid.core.design.patterns.filetypeselection.SelectableFileType
import com.ikurek.scandroid.features.savedscans.data.model.SavedScanFiles
import com.ikurek.scandroid.features.savedscans.usecase.DeleteSavedScan
import com.ikurek.scandroid.features.savedscans.usecase.GetExtendedInfoFromSavedScan
import com.ikurek.scandroid.features.savedscans.usecase.GetSavedScan
import com.ikurek.scandroid.features.savedscans.usecase.MarkScanAsViewed
import com.ikurek.scandroid.features.scandetails.ScanDetailsScreenArgs
import com.ikurek.scandroid.features.scandetails.ui.scandetails.model.PdfAndImagesTabs
import com.ikurek.scandroid.features.scandetails.ui.scandetails.model.SavedScanState
import com.ikurek.scandroid.features.scandetails.usecase.OpenScanFilesOutside
import com.ikurek.scandroid.features.scandetails.usecase.SelectedFileType
import com.ikurek.scandroid.features.scandetails.usecase.ShareScanFiles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("LongParameterList")
@HiltViewModel
internal class ScanDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSavedScan: GetSavedScan,
    private val markScanAsViewed: MarkScanAsViewed,
    private val openScanFilesOutside: OpenScanFilesOutside,
    private val shareScanFiles: ShareScanFiles,
    private val deleteSavedScan: DeleteSavedScan,
    private val getExtendedInfoFromSavedScan: GetExtendedInfoFromSavedScan
) : ViewModel() {

    private val args: ScanDetailsScreenArgs = ScanDetailsScreenArgs(savedStateHandle)

    private val _dialog: MutableStateFlow<ScanDetailsDialog?> = MutableStateFlow(null)
    val dialog: StateFlow<ScanDetailsDialog?> = _dialog

    private val _sideEffects: Channel<ScanDetailsSideEffect> = Channel()
    val sideEffects: Flow<ScanDetailsSideEffect> = _sideEffects.receiveAsFlow()

    private val _scanState: MutableStateFlow<SavedScanState> =
        MutableStateFlow(SavedScanState.Loading)
    val scanState: StateFlow<SavedScanState> = _scanState

    private var currentPage: PdfAndImagesTabs? = null

    fun onScreenEnter() = viewModelScope.launch {
        getSavedScan(args.scanId).onSuccess {
            _scanState.value = SavedScanState.Loaded(it)
            markScanAsViewed(it.id)
        }.onFailure {
            _scanState.value = SavedScanState.Error
        }
    }

    fun onDeleteScanClick() {
        _dialog.value = ScanDetailsDialog.DeleteScanConfirmation
    }

    fun onConfirmDeleteScanClick() = viewModelScope.launch {
        _dialog.value = null
        deleteSavedScan(args.scanId)
        _sideEffects.send(ScanDetailsSideEffect.ScanDeleted)
    }

    fun onScanInfoClick() {
        val scan = (_scanState.value as SavedScanState.Loaded).scan
        _dialog.value = ScanDetailsDialog.ExtendedScanInformation(
            extendedScanInfo = getExtendedInfoFromSavedScan(scan)
        )
    }

    fun onOpenOutsideClick() = viewModelScope.launch {
        val scan = (_scanState.value as SavedScanState.Loaded).scan
        when (scan.files) {
            is SavedScanFiles.ImagesOnly -> openScanFilesOutside(
                scan.files,
                SelectedFileType.Images
            )

            is SavedScanFiles.PdfOnly -> openScanFilesOutside(scan.files, SelectedFileType.PDF)
            is SavedScanFiles.PdfAndImages -> {
                _dialog.value = ScanDetailsDialog.OpenFileTypeSelection
            }
        }
    }

    fun onOpenFileTypeSelect(fileType: SelectableFileType) = viewModelScope.launch {
        val scan = (_scanState.value as SavedScanState.Loaded).scan
        _dialog.value = null
        when (fileType) {
            SelectableFileType.Images -> openScanFilesOutside(scan.files, SelectedFileType.Images)
            SelectableFileType.Document -> openScanFilesOutside(scan.files, SelectedFileType.PDF)
        }
    }

    fun onShareFilesClick() = viewModelScope.launch {
        val scan = (_scanState.value as SavedScanState.Loaded).scan
        when (scan.files) {
            is SavedScanFiles.ImagesOnly -> shareScanFiles(scan.files, SelectedFileType.Images)
            is SavedScanFiles.PdfOnly -> shareScanFiles(scan.files, SelectedFileType.PDF)
            is SavedScanFiles.PdfAndImages -> {
                _dialog.value = ScanDetailsDialog.ShareFileTypeSelection
            }
        }
    }

    fun onShareFileTypeSelect(fileType: SelectableFileType) = viewModelScope.launch {
        val scan = (_scanState.value as SavedScanState.Loaded).scan
        _dialog.value = null
        when (fileType) {
            SelectableFileType.Images -> shareScanFiles(scan.files, SelectedFileType.Images)
            SelectableFileType.Document -> shareScanFiles(scan.files, SelectedFileType.PDF)
        }
    }

    fun onFileTypePageChange(newPage: PdfAndImagesTabs) = viewModelScope.launch {
        currentPage = newPage
    }

    fun onDialogDismiss() {
        _dialog.value = null
    }
}
