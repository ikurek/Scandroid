package com.ikurek.scandroid.features.createscan

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ikurek.scandroid.core.design.components.dialogs.ErrorDialog
import com.ikurek.scandroid.features.createscan.data.model.ScanFileFormat
import com.ikurek.scandroid.features.createscan.data.model.ScannedDocuments
import com.ikurek.scandroid.features.createscan.ui.newscan.NewScanDialog
import com.ikurek.scandroid.features.createscan.ui.newscan.NewScanScreen
import com.ikurek.scandroid.features.createscan.ui.newscan.NewScanSideEffect
import com.ikurek.scandroid.features.createscan.ui.newscan.NewScanViewModel
import com.ikurek.scandroid.features.createscan.ui.newscan.model.DescriptionInput
import com.ikurek.scandroid.features.createscan.ui.newscan.model.DocumentNameInput
import java.util.UUID

const val NewScanRoute = "new-scan"

fun NavGraphBuilder.newScanScreen(
    onScanCreated: (scanId: UUID) -> Unit,
    onNavigateUp: () -> Unit
) {
    composable(route = NewScanRoute) {
        val viewModel: NewScanViewModel = hiltViewModel()
        val dialog: NewScanDialog? by viewModel.dialog.collectAsState()
        val scannedDocuments: ScannedDocuments by viewModel.scannedDocuments.collectAsState()
        val documentName: DocumentNameInput by viewModel.documentName.collectAsState()
        val description: DescriptionInput by viewModel.description.collectAsState()
        val fileFormats: Map<ScanFileFormat, Boolean> by viewModel.fileFormats.collectAsState()
        val isSaving: Boolean by viewModel.isSaving.collectAsState()
        val isSaveButtonEnabled: Boolean by viewModel.isSaveButtonEnabled.collectAsState()

        LaunchedEffect(viewModel.sideEffects) {
            viewModel.sideEffects.collect { sideEffect ->
                when (sideEffect) {
                    is NewScanSideEffect.ScanCreated -> onScanCreated(sideEffect.scanId)
                }
            }
        }

        dialog?.let {
            NewScanScreenDialog(
                dialog = it,
                onDismissRequest = viewModel::onDialogDismissed,
            )
        }

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
            onSaveButtonClick = viewModel::onSaveButtonClick,
            onNavigateUp = onNavigateUp
        )
    }
}

@Composable
private fun NewScanScreenDialog(
    dialog: NewScanDialog,
    onDismissRequest: () -> Unit,
) {
    when (dialog) {
        is NewScanDialog.Error -> ErrorDialog(
            title = stringResource(id = dialog.titleRes),
            content = stringResource(id = dialog.contentRes),
            exception = dialog.exception,
            onDismissRequest = onDismissRequest
        )
    }
}

fun NavController.navigateToNewScan() {
    navigate(NewScanRoute)
}
