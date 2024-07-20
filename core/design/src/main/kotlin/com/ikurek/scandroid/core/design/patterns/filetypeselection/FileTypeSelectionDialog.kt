package com.ikurek.scandroid.core.design.patterns.filetypeselection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.core.design.components.dialogs.internal.ButtonAction
import com.ikurek.scandroid.core.design.components.dialogs.internal.MaterialDialog
import com.ikurek.scandroid.core.translations.R

@Composable
fun FileTypeSelectionDialog(
    onFileTypeSelect: (SelectableFileType) -> Unit,
    onDismiss: () -> Unit
) {
    MaterialDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.dialog_select_file_type_title),
                style = MaterialTheme.typography.headlineSmall,
                color = AlertDialogDefaults.textContentColor
            )
        },
        content = {
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                SelectableFileType.entries.forEach { selectableFileType ->
                    SelectableFileTypeRow(
                        selectableFileType = selectableFileType,
                        onClick = { onFileTypeSelect(selectableFileType) }
                    )
                }
            }
        },
        actions = {
            ButtonAction(
                text = stringResource(id = R.string.dialog_button_cancel),
                onClick = onDismiss
            )
        }
    )
}

@Composable
private fun RowScope.SelectableFileTypeRow(
    selectableFileType: SelectableFileType,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = when (selectableFileType) {
                    SelectableFileType.Images -> Icons.Default.Collections
                    SelectableFileType.Document -> Icons.AutoMirrored.Default.InsertDriveFile
                },
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(
                    id = when (selectableFileType) {
                        SelectableFileType.Images -> R.string.dialog_select_file_type_images_label
                        SelectableFileType.Document -> R.string.dialog_select_file_type_document_label
                    }
                ),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    ScandroidTheme {
        FileTypeSelectionDialog(
            onFileTypeSelect = {},
            onDismiss = {}
        )
    }
}
