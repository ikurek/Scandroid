package com.ikurek.scandroid.core.design.components.divider

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ikurek.scandroid.core.design.ScandroidTheme

@Composable
fun DividerWithIcon(text: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
        Spacer(modifier = Modifier.width(8.dp))
        HorizontalDivider()
    }
}

@Composable
@PreviewLightDark
private fun Preview() {
    ScandroidTheme {
        DividerWithIcon(
            text = "Name",
            icon = Icons.Default.Image,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}
