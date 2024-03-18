package com.ikurek.scandroid.core.design.components.dialogs

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.core.translations.R as TranslationsR

@Suppress("LongMethod")
@Composable
internal fun BasicDialog(
    onDismissRequest: () -> Unit,
    title: String? = null,
    content: String? = null,
    icon: (@Composable () -> Unit)? = null,
    expandableText: String? = null
) {
    var isExpandableTextVisible: Boolean by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            shape = AlertDialogDefaults.shape,
            color = AlertDialogDefaults.containerColor,
            tonalElevation = AlertDialogDefaults.TonalElevation,
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .fillMaxWidth()
            ) {
                icon?.let {
                    CompositionLocalProvider(LocalContentColor provides AlertDialogDefaults.iconContentColor) {
                        Box(
                            Modifier
                                .padding(bottom = 16.dp)
                                .align(Alignment.CenterHorizontally)
                        ) {
                            icon()
                        }
                    }
                }
                title?.let {
                    Box(
                        Modifier
                            .padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
                            .align(
                                if (icon == null) {
                                    Alignment.Start
                                } else {
                                    Alignment.CenterHorizontally
                                }
                            )
                    ) {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.headlineSmall,
                            color = AlertDialogDefaults.textContentColor
                        )
                    }
                }
                content?.let {
                    Box(
                        Modifier
                            .weight(weight = 1f, fill = false)
                            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
                            .align(Alignment.Start)
                    ) {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            color = AlertDialogDefaults.textContentColor
                        )
                    }
                }
                expandableText?.let {
                    Column(
                        Modifier.animateContentSize()
                    ) {
                        TextButton(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            onClick = { isExpandableTextVisible = !isExpandableTextVisible }
                        ) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = if (isExpandableTextVisible) {
                                    Icons.Default.ExpandLess
                                } else {
                                    Icons.Default.ExpandMore
                                },
                                tint = AlertDialogDefaults.textContentColor,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = stringResource(id = TranslationsR.string.dialog_button_show_details),
                                style = MaterialTheme.typography.bodyMedium,
                                color = AlertDialogDefaults.textContentColor
                            )
                        }

                        if (isExpandableTextVisible) {
                            Box(
                                Modifier
                                    .weight(weight = 1f, fill = false)
                                    .padding(horizontal = 24.dp)
                                    .align(Alignment.Start)
                            ) {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = AlertDialogDefaults.textContentColor
                                )
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .align(Alignment.End)
                ) {
                    TextButton(
                        onClick = onDismissRequest
                    ) {
                        Text(
                            text = stringResource(id = TranslationsR.string.dialog_button_neutral),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
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
    ScandroidTheme {
        BasicDialog(
            title = "Title",
            content = "Content",
            expandableText = "Expanded text",
            onDismissRequest = {}
        )
    }
}
