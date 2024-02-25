package com.ikurek.scandroid.core.design

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext

@Composable
fun ScandroidTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = scandroidColorScheme(),
        typography = Typography(),
        content = content
    )
}

@Composable
@ReadOnlyComposable
private fun scandroidColorScheme(): ColorScheme {
    val context = LocalContext.current
    val isSystemInDarkTheme = isSystemInDarkTheme()
    return when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (isSystemInDarkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }

        isSystemInDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
}
