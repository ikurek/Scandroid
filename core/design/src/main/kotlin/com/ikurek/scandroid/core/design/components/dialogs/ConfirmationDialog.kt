package com.ikurek.scandroid.core.design.components.dialogs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.core.design.components.dialogs.internal.ButtonAction
import com.ikurek.scandroid.core.design.components.dialogs.internal.MaterialDialog
import com.ikurek.scandroid.core.translations.R

@Composable
fun ConfirmationDialog(
    title: String,
    content: String,
    icon: ImageVector,
    onConfirmRequest: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    MaterialDialog(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = AlertDialogDefaults.textContentColor
            )
        },
        content = {
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = AlertDialogDefaults.textContentColor
            )
        },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        },
        onDismissRequest = onDismissRequest,
        actions = {
            ButtonAction(
                text = stringResource(id = R.string.dialog_button_cancel),
                onClick = onDismissRequest
            )
            ButtonAction(
                text = stringResource(id = R.string.dialog_button_confirm),
                onClick = { onConfirmRequest() }
            )
        }
    )
}

@PreviewLightDark
@Composable
private fun Preview() {
    ScandroidTheme {
        ConfirmationDialog(
            title = "Title",
            content = "Content",
            icon = Icons.Default.Info,
            onDismissRequest = {},
            onConfirmRequest = {}
        )
    }
}
