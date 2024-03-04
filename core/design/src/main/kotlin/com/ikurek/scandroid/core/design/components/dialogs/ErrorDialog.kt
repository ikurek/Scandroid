package com.ikurek.scandroid.core.design.components.dialogs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.ikurek.scandroid.core.design.ScandroidTheme

@Composable
fun ErrorDialog(
    onDismissRequest: () -> Unit,
    title: String? = null,
    content: String? = null
) {
    BasicDialog(
        title = title,
        content = content,
        onDismissRequest = onDismissRequest,
        icon = {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = null
            )
        },
    )
}

@PreviewLightDark
@Composable
private fun Preview() {
    ScandroidTheme {
        ErrorDialog(title = "Title", content = "Content", onDismissRequest = {})
    }
}
