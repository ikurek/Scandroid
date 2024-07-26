package com.ikurek.scandroid.core.design.components.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.ikurek.scandroid.core.translations.R as TranslationsR

@Composable
fun SearchInput(
    label: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    initialValue: String = ""
) {
    var value by remember { mutableStateOf(initialValue) }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(value) { onValueChange(value) }

    BasicInput(
        value = value,
        onValueChange = { value = it },
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        label = label,
        placeholder = stringResource(TranslationsR.string.search_input_placeholder),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions { focusManager.clearFocus() },
        maxLines = 1,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = { value = "" }) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = stringResource(
                            id = TranslationsR.string.search_input_clear_button_content_descriptions
                        )
                    )
                }
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun PreviewEmpty() {
    SearchInput(label = "Search for something", onValueChange = {})
}

@PreviewLightDark
@Composable
private fun PreviewFilled() {
    SearchInput(label = "Search for something", initialValue = "Test", onValueChange = {})
}
