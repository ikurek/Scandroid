package com.ikurek.scandroid.core.design.components.placeholders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ikurek.scandroid.core.design.ScandroidTheme

@Composable
fun ScreenPlaceholder(
    imageVector: ImageVector,
    imageContentDescription: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 48.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = imageContentDescription,
            modifier = Modifier.size(128.dp),
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    ScandroidTheme {
        ScreenPlaceholder(
            imageVector = Icons.Default.Android,
            imageContentDescription = "Content Description",
            label = "Label"
        )
    }
}
