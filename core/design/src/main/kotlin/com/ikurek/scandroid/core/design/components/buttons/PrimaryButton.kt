package com.ikurek.scandroid.core.design.components.buttons

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ikurek.scandroid.core.design.ScandroidTheme

@Composable
fun PrimaryButton(
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isLoading: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = if (isLoading) true else isEnabled,
    ) {
        AnimatedContent(
            targetState = isLoading,
            label = "PrimaryButtonLoadingAnimatedContent",
            contentAlignment = Alignment.Center
        ) { isLoadingState ->
            if (isLoadingState) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(text = value)
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun Preview() {
    ScandroidTheme {
        PrimaryButton(
            value = "Text",
            onClick = { }
        )
    }
}

@Composable
@PreviewLightDark
private fun PreviewDisabled() {
    ScandroidTheme {
        PrimaryButton(
            value = "Text",
            onClick = { },
            isEnabled = false
        )
    }
}

@Composable
@PreviewLightDark
private fun PreviewLoading() {
    ScandroidTheme {
        PrimaryButton(
            value = "Text",
            onClick = { },
            isEnabled = false,
            isLoading = true
        )
    }
}
