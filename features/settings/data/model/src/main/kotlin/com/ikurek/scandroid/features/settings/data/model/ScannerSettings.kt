package com.ikurek.scandroid.features.settings.data.model

data class ScannerSettings(
    val scannerMode: ScannerMode,
    val scannerFormats: ScannerFormats
)

enum class ScannerMode(val value: String) {
    Base("base"), BaseWithFilter("base_with_filter"), Full("full");

    companion object {
        val Default = Full

        fun valueOrDefault(value: String) = entries.find { it.value == value }
    }
}

enum class ScannerFormats(val value: String) {
    JpegOnly("jpeg_only"), PdfOnly("pdf_only"), JpegAndPdf("jpeg_and_pdf");

    companion object {
        val Default = JpegAndPdf

        fun fromValue(value: String) = ScannerFormats.entries.find { it.value == value }
    }
}
