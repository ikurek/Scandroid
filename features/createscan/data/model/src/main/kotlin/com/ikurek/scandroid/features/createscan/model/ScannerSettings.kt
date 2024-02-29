package com.ikurek.scandroid.features.createscan.model

data class ScannerSettings(
    val scannerMode: ScannerMode,
    val supportedFormats: List<ScannerFormat>
) {

    companion object {
        val Default = ScannerSettings(
            scannerMode = ScannerMode.Full,
            supportedFormats = ScannerFormat.entries
        )
    }
}
