package com.ikurek.scandroid.features.createscan

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ikurek.scandroid.features.createscan.model.ScannedDocuments
import com.ikurek.scandroid.features.createscan.model.ScannerFileFormat
import com.ikurek.scandroid.features.createscan.ui.newscan.NewScanScreen
import com.ikurek.scandroid.features.createscan.ui.newscan.NewScanViewModel
import com.ikurek.scandroid.features.createscan.ui.newscan.model.DescriptionInput
import com.ikurek.scandroid.features.createscan.ui.newscan.model.DocumentNameInput

const val NewScanRoute = "new-scan"

fun NavGraphBuilder.newScanScreen() {
    composable(route = NewScanRoute) {
        val viewModel: NewScanViewModel = hiltViewModel()
        val scannedDocuments: ScannedDocuments by viewModel.scannedDocuments.collectAsState()
        val documentName: DocumentNameInput by viewModel.documentName.collectAsState()
        val description: DescriptionInput by viewModel.description.collectAsState()
        val fileFormats: Map<ScannerFileFormat, Boolean> by viewModel.fileFormats.collectAsState()
        val isSaving: Boolean by viewModel.isSaving.collectAsState()
        val isSaveButtonEnabled: Boolean by viewModel.isSaveButtonEnabled.collectAsState()

        NewScanScreen(
            scannedDocuments = scannedDocuments,
            documentName = documentName,
            onDocumentNameChange = viewModel::onDocumentNameChange,
            onClearDocumentNameClick = viewModel::onClearDocumentNameClick,
            description = description,
            onDescriptionChange = viewModel::onDescriptionChange,
            onClearDescriptionClick = viewModel::onClearDescriptionClick,
            fileFormats = fileFormats,
            onFileFormatSelectionChange = viewModel::onFileFormatSelectionChange,
            isSaving = isSaving,
            isSaveButtonEnabled = isSaveButtonEnabled,
            onSaveButtonClick = viewModel::onSaveButtonClick
        )
    }
}

fun NavController.navigateToNewScanScreen() {
    navigate(NewScanRoute)
}
