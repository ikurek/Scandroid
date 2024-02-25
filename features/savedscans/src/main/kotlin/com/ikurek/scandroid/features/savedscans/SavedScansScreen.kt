package com.ikurek.scandroid.features.savedscans

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Scanner
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.core.translations.R as TranslationsR

@Composable
fun SavedScansScreen(
    scans: List<String>,
    onCreateScanClick: () -> Unit,
) {
    Scaffold(
        topBar = { TopAppBar() },
        floatingActionButton = { CreateScanFloatingActionButton(onClick = onCreateScanClick) }
    ) { contentPadding ->
        AnimatedContent(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
            targetState = scans.isEmpty(),
            label = "SavedScansScreen_AnimatedContent"
        ) { isEmpty ->
            if (isEmpty) {
                EmptyScreenPlaceholder()
            } else {
                ScanList(scans)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar() {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(TranslationsR.string.app_name)) }
    )
}

@Composable
private fun CreateScanFloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.DocumentScanner,
            contentDescription = stringResource(
                id = TranslationsR.string.saved_scans_create_scan_fab_content_descriptions
            ),
        )
    }
}

@Composable
private fun EmptyScreenPlaceholder() {
    Column(
        modifier = Modifier
            .padding(horizontal = 48.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Scanner,
            contentDescription = stringResource(
                id = TranslationsR.string.saved_scans_empty_placeholder_content_descriptions
            ),
            modifier = Modifier.size(128.dp),
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(id = TranslationsR.string.saved_scans_empty_placeholder_label),
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
    }
}

@Suppress("UnusedParameter")
@Composable
private fun ScanList(scans: List<String>) {
    // TODO: Implement
    Box(modifier = Modifier.fillMaxSize())
}

@PreviewLightDark
@Composable
private fun PreviewEmpty() {
    ScandroidTheme {
        SavedScansScreen(
            scans = emptyList(),
            onCreateScanClick = {}
        )
    }
}
