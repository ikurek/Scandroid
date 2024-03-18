package com.ikurek.scandroid.features.createscan.ui.newscan.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ikurek.scandroid.core.design.components.inputs.BasicInput
import com.ikurek.scandroid.core.translations.R
import com.ikurek.scandroid.features.createscan.ui.newscan.model.DescriptionInput

@Composable
internal fun DescriptionInput(
    description: DescriptionInput,
    onDescriptionChange: (String) -> Unit,
    onClearDescriptionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BasicInput(
        value = description.value,
        onValueChange = onDescriptionChange,
        modifier = modifier,
        label = stringResource(id = R.string.new_scan_document_description_label),
        trailingIcon = {
            (description as? DescriptionInput.Filled)?.let {
                IconButton(onClick = onClearDescriptionClick) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = stringResource(
                            id = R.string.new_scan_document_description_clear_button_content_descriptions
                        )
                    )
                }
            }
        }
    )
}
