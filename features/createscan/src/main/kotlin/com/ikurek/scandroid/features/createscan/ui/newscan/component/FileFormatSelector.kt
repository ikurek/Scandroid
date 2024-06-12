package com.ikurek.scandroid.features.createscan.ui.newscan.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.features.createscan.data.model.ScanFileFormat
import com.ikurek.scandroid.core.translations.R as TranslationsR

@Composable
internal fun FileFormatSelector(
    fileFormats: Map<ScanFileFormat, Boolean>,
    onFileFormatSelectionChange: (format: ScanFileFormat, isSelected: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        fileFormats.forEach { (fileFormat, isSelected) ->
            FileFormatCheckbox(
                fileFormat = fileFormat,
                isSelected = isSelected,
                onCheckedChange = { onFileFormatSelectionChange(fileFormat, it) }
            )
        }
    }
}

@Composable
private fun FileFormatCheckbox(
    fileFormat: ScanFileFormat,
    isSelected: Boolean,
    onCheckedChange: (isChecked: Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = isSelected, onCheckedChange = onCheckedChange)
        Text(
            text = stringResource(
                id = when (fileFormat) {
                    ScanFileFormat.JPEG -> TranslationsR.string.common_file_format_jpeg
                    ScanFileFormat.PDF -> TranslationsR.string.common_file_format_pdf
                }
            )
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    ScandroidTheme {
        FileFormatSelector(
            fileFormats = mapOf(ScanFileFormat.PDF to true, ScanFileFormat.JPEG to false),
            onFileFormatSelectionChange = { _, _ -> }
        )
    }
}
