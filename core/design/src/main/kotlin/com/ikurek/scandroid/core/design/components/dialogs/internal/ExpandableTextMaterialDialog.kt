package com.ikurek.scandroid.core.design.components.dialogs.internal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.core.translations.R as TranslationsR

@Suppress("LongMethod")
@Composable
internal fun ExpandableTextMaterialDialog(
    onDismissRequest: () -> Unit,
    title: String,
    content: String,
    icon: (@Composable () -> Unit)? = null,
    expandableText: String? = null
) {
    var isExpanded: Boolean by remember { mutableStateOf(false) }

    DialogContent(
        onDismissRequest = onDismissRequest,
        title = title,
        content = content,
        icon = icon,
        expandableText = expandableText,
        isExpanded = isExpanded,
        onExpandButtonClick = { isExpanded = !isExpanded }
    )
}

@Composable
private fun DialogContent(
    onDismissRequest: () -> Unit,
    title: String,
    content: String,
    icon: (@Composable () -> Unit)?,
    expandableText: String?,
    isExpanded: Boolean,
    onExpandButtonClick: () -> Unit,
) {
    MaterialDialog(
        onDismissRequest = onDismissRequest,
        icon = icon,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = AlertDialogDefaults.textContentColor
            )
        },
        content = {
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = AlertDialogDefaults.textContentColor
            )

            expandableText?.let {
                ExpandableContent(
                    expandableText = expandableText,
                    isExpanded = isExpanded,
                    onExpandButtonClick = onExpandButtonClick
                )
            }
        }
    )
}

@Composable
private fun ExpandableContent(
    expandableText: String,
    isExpanded: Boolean,
    onExpandButtonClick: () -> Unit,
) {
    Column(
        Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(CornerSize(4.dp)))
                .clickable(onClick = onExpandButtonClick),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(
                    id = if (isExpanded) {
                        TranslationsR.string.dialog_button_hide_details
                    } else {
                        TranslationsR.string.dialog_button_show_details
                    }
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = AlertDialogDefaults.textContentColor
            )

            Spacer(modifier = Modifier.width(2.dp))

            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = if (isExpanded) {
                    Icons.Default.ExpandLess
                } else {
                    Icons.Default.ExpandMore
                },
                tint = AlertDialogDefaults.textContentColor,
                contentDescription = null
            )
        }

        if (isExpanded) {
            Box(
                Modifier
                    .weight(weight = 1f, fill = false)
                    .align(Alignment.Start)
            ) {
                Text(
                    text = expandableText,
                    style = MaterialTheme.typography.bodySmall,
                    color = AlertDialogDefaults.textContentColor
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewCollapsed() {
    ScandroidTheme {
        DialogContent(
            title = "Title",
            content = "Content",
            expandableText = "Expanded text that can be very, very, very, very, very, very long",
            icon = null,
            isExpanded = false,
            onDismissRequest = {},
            onExpandButtonClick = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewExpanded() {
    ScandroidTheme {
        DialogContent(
            title = "Title",
            content = "Content",
            expandableText = "Expanded text that can be very, very, very, very, very, very long",
            icon = null,
            isExpanded = true,
            onDismissRequest = {},
            onExpandButtonClick = {}
        )
    }
}
