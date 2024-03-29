package com.ikurek.scandroid.features.createscan.data.model

data class ScannerSettings(
    val scannerMode: ScannerMode,
    val supportedFormats: List<ScannerFileFormat>
) {

    companion object {
        val Default = ScannerSettings(
            scannerMode = ScannerMode.Full,
            supportedFormats = ScannerFileFormat.entries
        )
    }
}
