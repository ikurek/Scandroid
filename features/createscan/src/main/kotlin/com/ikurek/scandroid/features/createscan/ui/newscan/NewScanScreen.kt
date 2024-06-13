package com.ikurek.scandroid.features.createscan.ui.newscan

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.core.design.components.appbar.PrimaryTopAppBar
import com.ikurek.scandroid.core.design.components.buttons.PrimaryButton
import com.ikurek.scandroid.core.design.components.divider.DividerWithIcon
import com.ikurek.scandroid.features.createscan.data.model.ScanFileFormat
import com.ikurek.scandroid.features.createscan.data.model.ScannedDocuments
import com.ikurek.scandroid.features.createscan.ui.newscan.component.DescriptionInput
import com.ikurek.scandroid.features.createscan.ui.newscan.component.DocumentNameInput
import com.ikurek.scandroid.features.createscan.ui.newscan.component.FileFormatSelector
import com.ikurek.scandroid.features.createscan.ui.newscan.model.DescriptionInput
import com.ikurek.scandroid.features.createscan.ui.newscan.model.DocumentNameInput
import com.ikurek.scandroid.features.createscan.ui.newscan.model.Section
import java.time.ZonedDateTime
import com.ikurek.scandroid.core.translations.R as TranslationsR

@Composable
internal fun NewScanScreen(
    scannedDocuments: ScannedDocuments,
    documentName: DocumentNameInput,
    onDocumentNameChange: (String) -> Unit,
    onClearDocumentNameClick: () -> Unit,
    description: DescriptionInput,
    onDescriptionChange: (String) -> Unit,
    onClearDescriptionClick: () -> Unit,
    fileFormats: Map<ScanFileFormat, Boolean>,
    onFileFormatSelectionChange: (ScanFileFormat, Boolean) -> Unit,
    isSaving: Boolean,
    isSaveButtonEnabled: Boolean,
    onSaveButtonClick: () -> Unit,
    onNavigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            PrimaryTopAppBar(
                title = stringResource(TranslationsR.string.new_scan_title),
                onNavigateUp = onNavigateUp
            )
        },
        bottomBar = {
            BottomBar(
                isSaving = isSaving,
                isSaveButtonEnabled = isSaveButtonEnabled,
                onSaveButtonClick = onSaveButtonClick
            )
        }
    ) { contentPadding ->
        Content(
            scannedDocuments = scannedDocuments,
            documentName = documentName,
            onDocumentNameChange = onDocumentNameChange,
            onClearDocumentNameClick = onClearDocumentNameClick,
            description = description,
            onDescriptionChange = onDescriptionChange,
            onClearDescriptionClick = onClearDescriptionClick,
            fileFormats = fileFormats,
            onFileFormatSelectionChange = onFileFormatSelectionChange,
            modifier = Modifier.padding(contentPadding)
        )
    }
}

@Composable
private fun Content(
    scannedDocuments: ScannedDocuments,
    documentName: DocumentNameInput,
    onDocumentNameChange: (String) -> Unit,
    onClearDocumentNameClick: () -> Unit,
    description: DescriptionInput,
    onDescriptionChange: (String) -> Unit,
    onClearDescriptionClick: () -> Unit,
    fileFormats: Map<ScanFileFormat, Boolean>,
    onFileFormatSelectionChange: (ScanFileFormat, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        SectionDivider(section = Section.Info)
        DocumentNameInput(
            documentName = documentName,
            onDocumentNameChange = onDocumentNameChange,
            onClearDocumentNameClick = onClearDocumentNameClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        DescriptionInput(
            description = description,
            onDescriptionChange = onDescriptionChange,
            onClearDescriptionClick = onClearDescriptionClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )

        // TODO: Implement tags support
        /*        SectionDivider(
                    section = Section.Tags,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )*/
        if (scannedDocuments.hasMultipleDocumentFormats) {
            SectionDivider(section = Section.Formats)
            FileFormatSelector(
                fileFormats = fileFormats,
                onFileFormatSelectionChange = onFileFormatSelectionChange,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}

@Composable
internal fun SectionDivider(section: Section) {
    DividerWithIcon(
        text = stringResource(section.sectionNameRes),
        icon = section.sectionIcon,
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
    )
}

@Composable
private fun BottomBar(
    isSaving: Boolean,
    isSaveButtonEnabled: Boolean,
    onSaveButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, bottom = 16.dp, top = 16.dp)
    ) {
        PrimaryButton(
            value = stringResource(id = TranslationsR.string.new_scan_title),
            onClick = onSaveButtonClick,
            modifier = Modifier.fillMaxWidth(),
            isLoading = isSaving,
            isEnabled = isSaveButtonEnabled
        )
    }
}

@Composable
@PreviewLightDark
private fun Preview() {
    ScandroidTheme {
        NewScanScreen(
            scannedDocuments = ScannedDocuments(
                createdAt = ZonedDateTime.now(),
                pdfUri = Uri.EMPTY,
                imageUris = listOf(Uri.EMPTY, Uri.EMPTY)
            ),
            documentName = DocumentNameInput.Filled("Name"),
            onDocumentNameChange = {},
            onClearDocumentNameClick = {},
            description = DescriptionInput.Filled("Description"),
            onDescriptionChange = {},
            onClearDescriptionClick = {},
            fileFormats = mapOf(ScanFileFormat.PDF to true, ScanFileFormat.JPEG to false),
            onFileFormatSelectionChange = { _, _ -> },
            isSaving = true,
            isSaveButtonEnabled = true,
            onSaveButtonClick = {},
            onNavigateUp = {}
        )
    }
}

@Composable
@PreviewLightDark
private fun PreviewEmptyDocumentName() {
    ScandroidTheme {
        NewScanScreen(
            scannedDocuments = ScannedDocuments(
                createdAt = ZonedDateTime.now(),
                pdfUri = Uri.EMPTY,
                imageUris = listOf(Uri.EMPTY, Uri.EMPTY)
            ),
            documentName = DocumentNameInput.Empty,
            onDocumentNameChange = {},
            onClearDocumentNameClick = {},
            description = DescriptionInput.Empty,
            onDescriptionChange = {},
            onClearDescriptionClick = {},
            fileFormats = mapOf(ScanFileFormat.PDF to true, ScanFileFormat.JPEG to false),
            onFileFormatSelectionChange = { _, _ -> },
            isSaving = false,
            isSaveButtonEnabled = false,
            onSaveButtonClick = {},
            onNavigateUp = {}
        )
    }
}
