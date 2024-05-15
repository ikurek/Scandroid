package com.ikurek.scandroid.core.design.components.appbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.core.translations.R as TranslationsR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryTopAppBar(
    title: String?,
    onNavigateUp: (() -> Unit)? = null,
    navigationIcon: @Composable () -> Unit = { BackArrowNavigationIcon(onNavigateUp) }
) {
    CenterAlignedTopAppBar(
        title = { title?.let { Text(text = title) } },
        navigationIcon = navigationIcon
    )
}

@Composable
private fun BackArrowNavigationIcon(onClick: (() -> Unit)?) {
    onClick?.let {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = stringResource(id = TranslationsR.string.common_back_button_icon)
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    ScandroidTheme {
        PrimaryTopAppBar(
            title = "Title",
            onNavigateUp = {}
        )
    }
}
