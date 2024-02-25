package com.ikurek.scandroid.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.core.translations.R as TranslationsR

@Composable
fun HomeScreen() {
    Scaffold(
        topBar = { TopAppBar() }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding).fillMaxSize())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar() {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(TranslationsR.string.app_name)) }
    )
}

@Preview
@Composable
private fun HomeScreenPreview() {
    ScandroidTheme {
        HomeScreen()
    }
}
