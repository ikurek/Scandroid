package com.ikurek.scandroid.features.settings.ui.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.core.design.components.appbar.PrimaryTopAppBar
import com.ikurek.scandroid.core.design.R as DesignR
import com.ikurek.scandroid.core.translations.R as TranslationsR

private const val IconWidthFraction = 1 / 3f
private val InfoRowMinHeight = 40.dp

@Composable
internal fun AboutScreen(
    state: AboutState,
    onNavigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            PrimaryTopAppBar(
                title = stringResource(TranslationsR.string.about_title),
                onNavigateUp = onNavigateUp
            )
        }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            Content(state = state)
        }
    }
}

@Composable
private fun Content(state: AboutState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth(IconWidthFraction)
                .aspectRatio(1f)
        ) {
            Image(
                painter = painterResource(DesignR.drawable.ic_launcher_foreground),
                contentDescription = stringResource(TranslationsR.string.app_icon_content_description),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(TranslationsR.string.app_name),
            style = MaterialTheme.typography.displaySmall,
        )

        Spacer(modifier = Modifier.height(24.dp))

        AboutInfoRow(
            text = stringResource(TranslationsR.string.about_item_version_label),
            value = state.version
        )

        HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp))

        AboutInfoRow(
            text = stringResource(TranslationsR.string.about_item_package_label),
            value = state.packageIdentifier
        )

        HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp))

        AboutClickableRow(
            text = stringResource(TranslationsR.string.about_item_open_source_licenses_label),
            onClick = {
                // TODO: Implement
            }
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun AboutInfoRow(text: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 24.dp)
            .defaultMinSize(minHeight = InfoRowMinHeight),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun AboutClickableRow(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 24.dp)
            .defaultMinSize(minHeight = InfoRowMinHeight),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(Icons.Default.ChevronRight, contentDescription = null)
    }
}

@PreviewLightDark
@Composable
private fun AboutScreenPreview() {
    ScandroidTheme {
        AboutScreen(
            state = AboutState(
                version = "1.0.0",
                packageIdentifier = "com.ikurek.scandroid"
            ),
            onNavigateUp = {}
        )
    }
}
