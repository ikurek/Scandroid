package com.ikurek.scandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ikurek.scandroid.core.design.ScandroidTheme
import com.ikurek.scandroid.feature.home.HomeScreen
import com.ikurek.scandroid.features.createscan.interactor.DocumentScannerInteractor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // I'm sorry this ever happened
    @Inject
    lateinit var documentScannerInteractor: DocumentScannerInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScandroidTheme {
                HomeScreen(
                    createScanRequest = documentScannerInteractor::createRequest,
                    parseScanResult = documentScannerInteractor::parseResult
                )
            }
        }
    }
}
