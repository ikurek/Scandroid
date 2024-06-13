package com.ikurek.scandroid.core.design.components.dialogs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.core.design.components.dialogs.internal.ExpandableTextMaterialDialog

@Composable
fun ErrorDialog(
    onDismissRequest: () -> Unit,
    title: String,
    content: String,
    exception: Throwable? = null
) {
    ExpandableTextMaterialDialog(
        title = title,
        content = content,
        onDismissRequest = onDismissRequest,
        icon = {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = null
            )
        },
        expandableText = exception?.message
    )
}

@PreviewLightDark
@Composable
private fun Preview() {
    ScandroidTheme {
        ErrorDialog(
            title = "Title",
            content = "Content",
            onDismissRequest = {},
            exception = IllegalStateException("Test message")
        )
    }
}
