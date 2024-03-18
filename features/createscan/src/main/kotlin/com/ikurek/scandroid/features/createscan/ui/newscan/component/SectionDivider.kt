package com.ikurek.scandroid.features.createscan.ui.newscan.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.features.createscan.ui.newscan.model.Section

@Composable
internal fun SectionDivider(section: Section, modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = section.sectionIcon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = stringResource(id = section.sectionNameRes))
        Spacer(modifier = Modifier.width(8.dp))
        HorizontalDivider()
    }
}

@Composable
@PreviewLightDark
private fun Preview() {
    ScandroidTheme {
        Column {
            Section.entries.forEach { section ->
                SectionDivider(section = section, modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}
