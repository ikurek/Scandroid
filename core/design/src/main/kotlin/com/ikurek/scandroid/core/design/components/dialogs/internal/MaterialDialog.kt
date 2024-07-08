package com.ikurek.scandroid.core.design.components.dialogs.internal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.core.translations.R

@OptIn(ExperimentalLayoutApi::class)
@Suppress("LongMethod")
@Composable
internal fun MaterialDialog(
    onDismissRequest: () -> Unit,
    title: (@Composable () -> Unit)? = null,
    content: (@Composable ColumnScope.() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = { DefaultButtonAction(onClick = onDismissRequest) },
) {
    Dialog(onDismissRequest = onDismissRequest) {
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
                        title()
                    }
                }
                content?.let {
                    Column(
                        Modifier
                            .weight(weight = 1f, fill = false)
                            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
                            .align(Alignment.Start)
                    ) {
                        content()
                    }
                }
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .align(Alignment.End)
                ) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        actions()
                    }
                }
            }
        }
    }
}

@Composable
private fun DefaultButtonAction(onClick: () -> Unit) {
    ButtonAction(
        text = stringResource(id = R.string.dialog_button_neutral),
        onClick = onClick
    )
}

@Composable
internal fun ButtonAction(text: String, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewWithIcon() {
    ScandroidTheme {
        MaterialDialog(
            title = {
                Text(
                    text = "Title",
                    style = MaterialTheme.typography.headlineSmall,
                    color = AlertDialogDefaults.textContentColor
                )
            },
            content = {
                Text(
                    text = "Content",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AlertDialogDefaults.textContentColor
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = null
                )
            },
            actions = {
                TextButton(onClick = { }) {
                    Text(
                        text = "Button 1",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                TextButton(onClick = {}) {
                    Text(
                        text = "Button 2",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            onDismissRequest = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewWithoutIcon() {
    ScandroidTheme {
        MaterialDialog(
            title = {
                Text(
                    text = "Title",
                    style = MaterialTheme.typography.headlineSmall,
                    color = AlertDialogDefaults.textContentColor
                )
            },
            content = {
                Text(
                    text = "Content",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AlertDialogDefaults.textContentColor
                )
            },
            actions = {
                TextButton(onClick = { }) {
                    Text(
                        text = "Button 1",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                TextButton(onClick = {}) {
                    Text(
                        text = "Button 2",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            onDismissRequest = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewWithoutActions() {
    ScandroidTheme {
        MaterialDialog(
            title = {
                Text(
                    text = "Title",
                    style = MaterialTheme.typography.headlineSmall,
                    color = AlertDialogDefaults.textContentColor
                )
            },
            content = {
                Text(
                    text = "Content",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AlertDialogDefaults.textContentColor
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = null
                )
            },
            onDismissRequest = {}
        )
    }
}
