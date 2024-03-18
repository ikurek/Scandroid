package com.ikurek.scandroid.features.scandetails.ui.scandetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.ikurek.scandroid.core.design.ScandroidTheme

@Composable
internal fun ScanDetailsScreen(
    scanId: String
) {
    Scaffold(
        topBar = { TopAppBar(scanId) }
    ) { contentPadding ->
        Content(
            modifier = Modifier.padding(contentPadding)
        )
    }
}

@Composable
private fun Content(modifier: Modifier = Modifier) {
    Box(modifier = modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(scanId: String) {
    CenterAlignedTopAppBar(
        title = { Text(scanId) }
    )
}

@PreviewLightDark
@Composable
private fun Preview() {
    ScandroidTheme {
        ScanDetailsScreen(scanId = "ScanId")
    }
}
