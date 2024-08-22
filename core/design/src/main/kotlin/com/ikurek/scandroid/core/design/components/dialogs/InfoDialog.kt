package com.ikurek.scandroid.core.design.components.dialogs

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.core.design.components.dialogs.internal.MaterialDialog

@Composable
fun InfoDialog(
    onDismissRequest: () -> Unit,
    title: String,
    content: (@Composable ColumnScope.() -> Unit)? = null,
) {
    MaterialDialog(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = AlertDialogDefaults.textContentColor
            )
        },
        content = content,
        icon = {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null
            )
        },
        onDismissRequest = onDismissRequest,
    )
}

@Composable
fun InfoDialog(
    onDismissRequest: () -> Unit,
    title: String,
    content: String
) {
    InfoDialog(
        title = title,
        content = {
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = AlertDialogDefaults.textContentColor
            )
        },
        onDismissRequest = onDismissRequest,
    )
}

@PreviewLightDark
@Composable
private fun Preview() {
    ScandroidTheme {
        InfoDialog(title = "Title", content = "Content", onDismissRequest = {})
    }
}
