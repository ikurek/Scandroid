package com.ikurek.scandroid.core.design.components.inputs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ikurek.scandroid.core.design.ScandroidTheme

@Composable
fun BasicInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    error: String? = null,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    trailingIcon: @Composable () -> Unit = {}
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = {
            if (label.isNullOrBlank().not()) {
                Text(text = label!!)
            }
        },
        trailingIcon = trailingIcon,
        isError = error.isNullOrBlank().not(),
        supportingText = {
            if (error.isNullOrBlank().not()) {
                Text(text = error!!)
            }
        },
        singleLine = maxLines == 1,
        maxLines = maxLines,
        minLines = minLines
    )
}

@Composable
@PreviewLightDark
private fun PreviewEmpty() {
    ScandroidTheme {
        Column {
            BasicInput(
                value = "",
                onValueChange = {},
                label = "Label"
            )

            Spacer(modifier = Modifier.height(16.dp))

            BasicInput(
                value = "",
                onValueChange = {},
                label = "Label",
                error = "Input can't be empty"
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun PreviewFilled() {
    ScandroidTheme {
        Column {
            BasicInput(
                value = "Value",
                onValueChange = {},
                label = "Label",
                trailingIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Cancel,
                            contentDescription = null
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            BasicInput(
                value = "Value",
                onValueChange = {},
                label = "Label",
                error = "Input can't be empty",
                trailingIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Cancel,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    }
}
