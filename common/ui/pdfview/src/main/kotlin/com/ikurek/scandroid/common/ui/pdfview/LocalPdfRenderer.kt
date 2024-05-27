package com.ikurek.scandroid.common.ui.pdfview

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File

class LocalPdfRenderer(file: File) {
    private lateinit var pdfRenderer: PdfRenderer
    private val renderedPageCache: MutableMap<Int, Bitmap> = mutableMapOf()

    private val _state: MutableStateFlow<PdfRendererState> =
        MutableStateFlow(PdfRendererState.Loading)
    val state: StateFlow<PdfRendererState> = _state

    init {
        runCatching {
            val parcelFileDescriptor =
                ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
            pdfRenderer = PdfRenderer(parcelFileDescriptor)
        }.onSuccess {
            _state.value = PdfRendererState.Loaded(pdfRenderer.pageCount)
        }.onFailure {
            _state.value = PdfRendererState.Error
        }
    }

    fun page(index: Int): Bitmap = renderedPageCache.getOrElse(index) {
        renderPage(index).also { page ->
            renderedPageCache[index] = page
        }
    }

    private fun renderPage(index: Int): Bitmap {
        pdfRenderer.openPage(index).use { page ->
            val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            return bitmap
        }
    }

    fun close() = pdfRenderer.close()
}
