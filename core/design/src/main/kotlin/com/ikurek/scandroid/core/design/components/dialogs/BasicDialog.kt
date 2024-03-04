package com.ikurek.scandroid.core.design.components.dialogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults
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
import com.ikurek.scandroid.core.translations.R as TranslationsR

@Suppress("LongMethod")
@Composable
internal fun BasicDialog(
    onDismissRequest: () -> Unit,
    title: String? = null,
    content: String? = null,
    icon: (@Composable () -> Unit)? = null,
) {
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
                    .padding(PaddingValues(all = 24.dp))
                    .fillMaxWidth()
            ) {
                icon?.let {
                    CompositionLocalProvider(LocalContentColor provides AlertDialogDefaults.iconContentColor) {
                        Box(
                            Modifier
                                .padding(PaddingValues(bottom = 16.dp))
                                .align(Alignment.CenterHorizontally)
                        ) {
                            icon()
                        }
                    }
                }
                title?.let {
                    Box(
                        Modifier
                            .padding(PaddingValues(bottom = 16.dp))
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
                            .padding(PaddingValues(bottom = 24.dp))
                            .align(Alignment.Start)
                    ) {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            color = AlertDialogDefaults.textContentColor
                        )
                    }
                }
                Box(modifier = Modifier.align(Alignment.End)) {
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
        BasicDialog(title = "Title", content = "Content", onDismissRequest = {})
    }
}
