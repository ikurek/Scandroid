package com.ikurek.scandroid.core.design.components.inputs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
    placeholder: String? = null,
    error: String? = null,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
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
        placeholder = {
            if (placeholder.isNullOrBlank().not()) {
                Text(text = placeholder!!)
            }
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = error.isNullOrBlank().not(),
        supportingText = {
            if (error.isNullOrBlank().not()) {
                Text(text = error!!)
            }
        },
        shape = RoundedCornerShape(8.dp),
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        singleLine = maxLines == 1,
        maxLines = maxLines,
        minLines = minLines,
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
