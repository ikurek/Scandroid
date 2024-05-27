package com.ikurek.scandroid.common.ui.pdfview

sealed interface PdfRendererState {
    data object Loading : PdfRendererState
    data class Loaded(val pageCount: Int) : PdfRendererState
    data object Error : PdfRendererState
}
