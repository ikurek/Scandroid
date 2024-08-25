package com.ikurek.scandroid.features.createscan.ui.newscan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikurek.scandroid.features.createscan.data.model.ScannedDocuments
import com.ikurek.scandroid.features.createscan.ui.newscan.mapper.FormatSelectorStateMapper
import com.ikurek.scandroid.features.createscan.ui.newscan.model.DescriptionInput
import com.ikurek.scandroid.features.createscan.ui.newscan.model.DocumentNameInput
import com.ikurek.scandroid.features.createscan.ui.newscan.model.FormatSelectorState
import com.ikurek.scandroid.features.createscan.usecase.CreateScanNameFromCurrentDate
import com.ikurek.scandroid.features.createscan.usecase.DeleteLatestUnsavedScan
import com.ikurek.scandroid.features.createscan.usecase.GetLatestUnsavedScan
import com.ikurek.scandroid.features.createscan.usecase.SaveScannedDocuments
import com.ikurek.scandroid.features.settings.data.model.ScannerFormats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class NewScanViewModel @Inject internal constructor(
    getLatestUnsavedScan: GetLatestUnsavedScan,
    createScanNameFromCurrentDate: CreateScanNameFromCurrentDate,
    private val saveScannedDocuments: SaveScannedDocuments,
    private val deleteLastUnsavedScan: DeleteLatestUnsavedScan,
    private val formatSelectorStateMapper: FormatSelectorStateMapper
) : ViewModel() {

    private val _dialog: MutableStateFlow<NewScanDialog?> = MutableStateFlow(null)
    val dialog: StateFlow<NewScanDialog?> = _dialog

    private val _sideEffects: Channel<NewScanSideEffect> = Channel()
    val sideEffects: Flow<NewScanSideEffect> = _sideEffects.receiveAsFlow()

    private val _scannedDocuments: MutableStateFlow<ScannedDocuments> = MutableStateFlow(
        getLatestUnsavedScan() ?: error("No unsaved scans found")
    )

    private val _documentName: MutableStateFlow<DocumentNameInput> = MutableStateFlow(
        DocumentNameInput.Filled(createScanNameFromCurrentDate(_scannedDocuments.value.createdAt))
    )
    val documentName: StateFlow<DocumentNameInput> = _documentName

    private val _description: MutableStateFlow<DescriptionInput> =
        MutableStateFlow(DescriptionInput.Empty)
    val description: StateFlow<DescriptionInput> = _description

    private val _formatsSelectorState: MutableStateFlow<FormatSelectorState> = MutableStateFlow(
        formatSelectorStateMapper.map(_scannedDocuments.value)
    )
    val formatsSelectorState: StateFlow<FormatSelectorState> = _formatsSelectorState

    val isSaveButtonEnabled: StateFlow<Boolean> = _documentName.map { documentName ->
        documentName is DocumentNameInput.Filled
    }.stateIn(viewModelScope, started = SharingStarted.Eagerly, initialValue = false)

    private val _isSaving: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving

    fun onDocumentNameChange(newDocumentName: String) {
        _documentName.value = if (newDocumentName.isBlank()) {
            DocumentNameInput.Empty
        } else {
            DocumentNameInput.Filled(newDocumentName)
        }
    }

    fun onClearDocumentNameClick() {
        _documentName.value = DocumentNameInput.Empty
    }

    fun onDescriptionChange(newDescription: String) {
        _description.value = if (newDescription.isBlank()) {
            DescriptionInput.Empty
        } else {
            DescriptionInput.Filled(newDescription)
        }
    }

    fun onClearDescriptionClick() {
        _description.value = DescriptionInput.Empty
    }

    fun onFileFormatSelectionChange(selectedFileFormats: ScannerFormats) {
        _formatsSelectorState.value = FormatSelectorState.Visible(selectedFileFormats)
    }

    fun onSaveButtonClick() = viewModelScope.launch {
        _isSaving.value = true
        val documentName: DocumentNameInput = _documentName.value
        val description: DescriptionInput = _description.value
        val scannedDocuments: ScannedDocuments = _scannedDocuments.value
        val selectedFileFormats: ScannerFormats? =
            (_formatsSelectorState.value as? FormatSelectorState.Visible)?.selectedScannerFormats
        saveScannedDocuments(
            name = documentName.value,
            description = description.value,
            scannedDocuments = scannedDocuments,
            selectedFileFormats = selectedFileFormats
        ).onSuccess { scanId ->
            deleteLastUnsavedScan()
            _sideEffects.send(NewScanSideEffect.ScanCreated(scanId))
        }.onFailure { exception ->
            _dialog.value = NewScanDialog.Error.ScanSaveFailed(exception)
        }
        _isSaving.value = false
    }

    fun onDialogDismissed() {
        _dialog.value = null
    }
}
