package com.ikurek.scandroid.features.createscan.ui.newscan.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ikurek.scandroid.core.design.components.inputs.BasicInput
import com.ikurek.scandroid.core.translations.R
import com.ikurek.scandroid.features.createscan.ui.newscan.model.DocumentNameInput

@Composable
internal fun DocumentNameInput(
    documentName: DocumentNameInput,
    onDocumentNameChange: (String) -> Unit,
    onClearDocumentNameClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BasicInput(
        value = documentName.value,
        onValueChange = onDocumentNameChange,
        modifier = modifier,
        maxLines = 1,
        label = stringResource(id = R.string.new_scan_document_name_label),
        error = (documentName as? DocumentNameInput.Empty)?.let {
            stringResource(id = R.string.new_scan_document_name_empty_error)
        },
        trailingIcon = {
            (documentName as? DocumentNameInput.Filled)?.let {
                IconButton(onClick = onClearDocumentNameClick) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = stringResource(
                            id = R.string.new_scan_document_name_clear_button_content_descriptions
                        )
                    )
                }
            }
        }
    )
}
