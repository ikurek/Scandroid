package com.ikurek.scandroid.features.createscan.ui.newscan.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.features.settings.data.model.ScannerFormats
import com.ikurek.scandroid.core.translations.R as TranslationsR

@Composable
internal fun FileFormatSelector(
    selectedScannerFormats: ScannerFormats,
    onFileFormatSelectionChange: (ScannerFormats) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        arrayOf(
            ScannerFormats.JpegAndPdf,
            ScannerFormats.JpegOnly,
            ScannerFormats.PdfOnly
        ).forEach { formats ->
            SelectableScannerFormat(
                formats = formats,
                isSelected = formats == selectedScannerFormats,
                onFileFormatSelectionChange = { onFileFormatSelectionChange(formats) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectableScannerFormat(
    formats: ScannerFormats,
    isSelected: Boolean,
    onFileFormatSelectionChange: () -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            RadioButton(
                selected = isSelected,
                onClick = onFileFormatSelectionChange
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = stringResource(
                when (formats) {
                    ScannerFormats.JpegOnly -> TranslationsR.string.new_scan_document_formats_jpeg_only_label
                    ScannerFormats.PdfOnly -> TranslationsR.string.new_scan_document_formats_pdf_only_label
                    ScannerFormats.JpegAndPdf -> TranslationsR.string.new_scan_document_formats_jpeg_and_pdf_label
                }
            ),
            modifier = Modifier.clickable(onClick = onFileFormatSelectionChange),
            style = MaterialTheme.typography.titleMedium,
            color = AlertDialogDefaults.textContentColor
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    ScandroidTheme {
        FileFormatSelector(
            selectedScannerFormats = ScannerFormats.JpegOnly,
            onFileFormatSelectionChange = {}
        )
    }
}
