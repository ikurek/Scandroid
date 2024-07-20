package com.ikurek.scandroid.core.design.components.appbar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.automirrored.filled.Dvr
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.core.translations.R

@Composable
fun PrimaryBottomAppBar(
    actions: @Composable RowScope.() -> Unit,
    floatingActionButton: @Composable (() -> Unit)? = null,
) {
    BottomAppBar(
        actions = actions,
        floatingActionButton = floatingActionButton,
        contentPadding = PaddingValues(start = 16.dp)
    )
}

@PreviewLightDark
@Composable
private fun Preview() {
    ScandroidTheme {
        PrimaryBottomAppBar(
            actions = {
                BottomAppBarAction(
                    icon = Icons.AutoMirrored.Default.List,
                    text = "List",
                    contentDescriptionRes = R.string.scan_details_action_open_outside_content_descriptions,
                    onClick = {}
                )
                Spacer(modifier = Modifier.width(16.dp))
                BottomAppBarAction(
                    icon = Icons.AutoMirrored.Default.Dvr,
                    text = "DVR",
                    contentDescriptionRes = R.string.scan_details_action_open_outside_content_descriptions,
                    onClick = {}
                )
                Spacer(modifier = Modifier.width(16.dp))
                BottomAppBarAction(
                    icon = Icons.AutoMirrored.Default.Article,
                    text = "Article",
                    contentDescriptionRes = R.string.scan_details_action_open_outside_content_descriptions,
                    onClick = {}
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {}
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }
        )
    }
}
