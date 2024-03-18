package com.ikurek.scandroid.features.createscan.ui.newscan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikurek.scandroid.features.createscan.model.ScannedDocuments
import com.ikurek.scandroid.features.createscan.model.ScannerFileFormat
import com.ikurek.scandroid.features.createscan.ui.newscan.model.DescriptionInput
import com.ikurek.scandroid.features.createscan.ui.newscan.model.DocumentNameInput
import com.ikurek.scandroid.features.createscan.usecase.CreateScanNameFromCurrentDate
import com.ikurek.scandroid.features.createscan.usecase.GetLatestUnsavedScan
import com.ikurek.scandroid.features.createscan.usecase.SaveScannedDocuments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class NewScanViewModel @Inject internal constructor(
    getLatestUnsavedScan: GetLatestUnsavedScan,
    createScanNameFromCurrentDate: CreateScanNameFromCurrentDate,
    private val saveScannedDocuments: SaveScannedDocuments
) : ViewModel() {

    private val _scannedDocuments: MutableStateFlow<ScannedDocuments> = MutableStateFlow(
        getLatestUnsavedScan() ?: error("No unsaved scans found")
    )
    val scannedDocuments: StateFlow<ScannedDocuments> = _scannedDocuments

    private val _documentName: MutableStateFlow<DocumentNameInput> = MutableStateFlow(
        DocumentNameInput.Filled(createScanNameFromCurrentDate())
    )
    val documentName: StateFlow<DocumentNameInput> = _documentName

    private val _description: MutableStateFlow<DescriptionInput> =
        MutableStateFlow(DescriptionInput.Empty)
    val description: StateFlow<DescriptionInput> = _description

    private val _fileFormats: MutableStateFlow<Map<ScannerFileFormat, Boolean>> = MutableStateFlow(
        buildMap {
            _scannedDocuments.value.let { scannedDocuments ->
                this[ScannerFileFormat.PDF] = scannedDocuments.pdfUri != null
                this[ScannerFileFormat.JPEG] = scannedDocuments.imageUris.isNotEmpty()
            }
        }
    )
    val fileFormats: StateFlow<Map<ScannerFileFormat, Boolean>> = _fileFormats

    val isSaveButtonEnabled: StateFlow<Boolean> =
        combine(_documentName, _fileFormats) { documentName, fileFormats ->
            documentName is DocumentNameInput.Filled && fileFormats.any { it.value }
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

    fun onFileFormatSelectionChange(fileFormat: ScannerFileFormat, isSelected: Boolean) {
        _fileFormats.value = _fileFormats.value.toMutableMap().apply {
            this[fileFormat] = isSelected
        }
    }

    fun onSaveButtonClick() = viewModelScope.launch {
        _isSaving.value = true
        val documentName: DocumentNameInput = _documentName.value
        val description: DescriptionInput = _description.value
        val scannedDocuments: ScannedDocuments = _scannedDocuments.value
        val selectedFileFormats: Set<ScannerFileFormat> = _fileFormats.value.toSetOfSelected()
        saveScannedDocuments(
            name = documentName.value,
            description = description.value,
            scannedDocuments = scannedDocuments,
            selectedFileFormats = selectedFileFormats
        )
        _isSaving.value = false
    }

    private fun Map<ScannerFileFormat, Boolean>.toSetOfSelected(): Set<ScannerFileFormat> =
        mapNotNull {
            if (it.value) {
                it.key
            } else {
                null
            }
        }.toSet()
}
