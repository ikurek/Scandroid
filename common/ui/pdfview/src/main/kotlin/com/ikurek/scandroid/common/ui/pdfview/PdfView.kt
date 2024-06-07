package com.ikurek.scandroid.common.ui.pdfview

import android.graphics.Bitmap
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ikurek.scandroid.core.design.ScandroidTheme
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable
import java.io.File
import com.ikurek.scandroid.core.translations.R as TranslationsR

@Composable
fun PdfView(
    file: File,
    modifier: Modifier = Modifier,
    placeholder: @Composable BoxScope.() -> Unit = { DefaultPlaceholder() },
    error: @Composable BoxScope.() -> Unit = { DefaultError() }
) {
    val renderer = remember(file) { LocalPdfRenderer(file) }
    val rendererState by renderer.state.collectAsState()

    DisposableEffect(file) {
        onDispose { renderer.close() }
    }

    PdfView(
        state = rendererState,
        onPageRequest = renderer::page,
        modifier = modifier,
        placeholder = placeholder,
        error = error
    )
}

@Composable
private fun PdfView(
    state: PdfRendererState,
    onPageRequest: (pageIndex: Int) -> Bitmap,
    modifier: Modifier = Modifier,
    placeholder: @Composable BoxScope.() -> Unit,
    error: @Composable BoxScope.() -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (state) {
            is PdfRendererState.Loaded -> PdfPager(
                pageCount = state.pageCount,
                onPageRequest = onPageRequest
            )

            is PdfRendererState.Error -> error()
            is PdfRendererState.Loading -> placeholder()
        }
    }
}

@Composable
private fun BoxScope.DefaultPlaceholder() {
    CircularProgressIndicator(
        modifier = Modifier.align(Alignment.Center)
    )
}

@Composable
private fun BoxScope.DefaultError() {
    Image(
        imageVector = Icons.Default.Warning,
        contentDescription = null,
        modifier = Modifier
            .align(Alignment.Center)
            .size(64.dp)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BoxScope.PdfPager(
    pageCount: Int,
    onPageRequest: (pageIndex: Int) -> Bitmap
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pageCount }
    )

    VerticalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { pageIndex ->
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = onPageRequest(pageIndex),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .zoomable(rememberZoomState())
            )
        }
    }

    Card(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .wrapContentSize()
            .align(Alignment.BottomStart)
    ) {
        Text(
            text = stringResource(
                TranslationsR.string.scan_details_pdf_page_count,
                pagerState.currentPage + 1,
                pagerState.pageCount
            ),
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewLoading() {
    ScandroidTheme {
        PdfView(
            state = PdfRendererState.Loading,
            onPageRequest = { Bitmap.createBitmap(0, 0, Bitmap.Config.ARGB_8888) },
            error = { DefaultError() },
            placeholder = { DefaultPlaceholder() }
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewError() {
    ScandroidTheme {
        PdfView(
            state = PdfRendererState.Error,
            onPageRequest = { Bitmap.createBitmap(0, 0, Bitmap.Config.ARGB_8888) },
            error = { DefaultError() },
            placeholder = { DefaultPlaceholder() }
        )
    }
}
