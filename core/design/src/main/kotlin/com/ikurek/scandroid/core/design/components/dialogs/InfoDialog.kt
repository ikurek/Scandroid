package com.ikurek.scandroid.core.design.components.dialogs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.ikurek.scandroid.core.design.ScandroidTheme

@Composable
fun InfoDialog(
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
                imageVector = Icons.Default.Info,
                contentDescription = null
            )
        },
    )
}

@PreviewLightDark
@Composable
private fun Preview() {
    ScandroidTheme {
        InfoDialog(title = "Title", content = "Content", onDismissRequest = {})
    }
}
