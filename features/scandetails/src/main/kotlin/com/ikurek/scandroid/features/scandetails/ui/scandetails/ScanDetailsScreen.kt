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
import com.ikurek.scandroid.features.savedscans.data.model.SavedScan
import com.ikurek.scandroid.features.savedscans.data.model.SavedScanFiles
import com.ikurek.scandroid.features.scandetails.ui.scandetails.model.SavedScanState
import java.io.File
import java.util.UUID

@Composable
internal fun ScanDetailsScreen(
    scanState: SavedScanState
) {
    Scaffold(
        topBar = { TopAppBar(scanState) }
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
private fun TopAppBar(scanState: SavedScanState) {
    CenterAlignedTopAppBar(
        title = {
            (scanState as? SavedScanState.Loaded)?.let { state ->
                Text(text = state.scan.name)
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun PreviewLoading() {
    ScandroidTheme {
        ScanDetailsScreen(scanState = SavedScanState.Loading)
    }
}

@PreviewLightDark
@Composable
private fun PreviewLoaded() {
    ScandroidTheme {
        ScanDetailsScreen(
            scanState = SavedScanState.Loaded(
                scan = SavedScan(
                    id = UUID.randomUUID(),
                    name = "Name",
                    description = "Scan description",
                    files = SavedScanFiles.PdfAndImages(
                        pdfFile = File("path"),
                        imageFiles = listOf(File("path"))
                    )
                )
            )
        )
    }
}
