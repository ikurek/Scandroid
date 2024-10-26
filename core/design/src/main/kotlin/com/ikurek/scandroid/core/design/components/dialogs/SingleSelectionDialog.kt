package com.ikurek.scandroid.core.design.components.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.core.design.components.dialogs.internal.ButtonAction
import com.ikurek.scandroid.core.design.components.dialogs.internal.MaterialDialog
import com.ikurek.scandroid.core.translations.R as TranslationsR

@Composable
fun <T> SingleSelectionDialog(
    title: String,
    initialValue: T,
    availableOptions: Set<SingleSelectionDialogOption<T>>,
    onConfirmRequest: (T) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var selectedValue: T by remember { mutableStateOf(initialValue) }

    MaterialDialog(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = AlertDialogDefaults.textContentColor
            )
        },
        content = {
            SelectionList(
                selectedValue = selectedValue,
                availableOptions = availableOptions,
                onOptionSelect = { selectedValue = it }
            )
        },
        onDismissRequest = onDismissRequest,
        actions = {
            ButtonAction(
                text = stringResource(id = TranslationsR.string.dialog_button_cancel),
                onClick = onDismissRequest
            )
            ButtonAction(
                text = stringResource(id = TranslationsR.string.dialog_button_save),
                onClick = { onConfirmRequest(selectedValue) }
            )
        }
    )
}

@Composable
private fun <T> SelectionList(
    selectedValue: T,
    availableOptions: Set<SingleSelectionDialogOption<T>>,
    onOptionSelect: (T) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        availableOptions.forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
                    RadioButton(
                        selected = option.value == selectedValue,
                        onClick = { onOptionSelect(option.value) }
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = { onOptionSelect(option.value) })
                ) {
                    Text(
                        text = option.text,
                        style = MaterialTheme.typography.titleMedium,
                        color = AlertDialogDefaults.textContentColor
                    )

                    option.description?.let {
                        Text(
                            text = option.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = AlertDialogDefaults.textContentColor
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    val options: List<SingleSelectionDialogOption<String>> by remember {
        mutableStateOf(
            listOf(
                SingleSelectionDialogOption(
                    value = "1",
                    text = "Option 1",
                    description = "Description 1"
                ),
                SingleSelectionDialogOption(
                    value = "2",
                    text = "Option 2",
                    description = "Description 2"
                ),
                SingleSelectionDialogOption(
                    value = "3",
                    text = "Option 3",
                    description = null
                ),
                SingleSelectionDialogOption(
                    value = "4",
                    text = "Option 4",
                    description = null
                ),
            )
        )
    }
    ScandroidTheme {
        SingleSelectionDialog(
            title = "Title",
            initialValue = options.first().value,
            availableOptions = options.toSet(),
            onConfirmRequest = {},
            onDismissRequest = {}
        )
    }
}
