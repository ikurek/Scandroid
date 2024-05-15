package com.ikurek.scandroid.features.scandetails.ui.scanimagegallery

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SdCardAlert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.ikurek.scandroid.core.design.components.appbar.PrimaryTopAppBar
import com.ikurek.scandroid.core.design.components.placeholders.ScreenPlaceholder
import com.ikurek.scandroid.core.translations.R
import com.ikurek.scandroid.features.scandetails.ui.scandetails.model.ScanImageGalleryState
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable
import com.ikurek.scandroid.core.translations.R as TranslationsR

@Composable
internal fun ScanImageGalleryScreen(
    imageGalleryState: ScanImageGalleryState,
    currentImageIndex: Int,
    onImageChange: (imageIndex: Int) -> Unit,
    onNavigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            ImageCountTopAppBar(
                imageGalleryState = imageGalleryState,
                currentImageIndex = currentImageIndex,
                onNavigateUp = onNavigateUp
            )
        }
    ) { contentPadding ->
        Crossfade(
            targetState = imageGalleryState,
            modifier = Modifier.padding(contentPadding),
            label = "ScanDetailsScreen_Crossfade"
        ) { state ->
            Box(modifier = Modifier.fillMaxSize()) {
                when (state) {
                    is ScanImageGalleryState.Error -> ErrorPlaceholder(
                        modifier = Modifier.align(Alignment.Center)
                    )

                    is ScanImageGalleryState.Loaded -> ScanImageGallery(
                        imageGalleryState = state,
                        currentImageIndex = currentImageIndex,
                        onImageChange = onImageChange,
                        modifier = Modifier.fillMaxSize()
                    )

                    is ScanImageGalleryState.Loading -> CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
private fun ImageCountTopAppBar(
    imageGalleryState: ScanImageGalleryState,
    currentImageIndex: Int,
    onNavigateUp: () -> Unit
) {
    val title = (imageGalleryState as? ScanImageGalleryState.Loaded)?.let { state ->
        stringResource(
            id = TranslationsR.string.scan_image_gallery_title,
            currentImageIndex + 1,
            state.images.size
        )
    }

    PrimaryTopAppBar(title = title, onNavigateUp = onNavigateUp)
}

@Composable
private fun ErrorPlaceholder(modifier: Modifier = Modifier) {
    ScreenPlaceholder(
        modifier = modifier,
        imageVector = Icons.Default.SdCardAlert,
        imageContentDescription = stringResource(
            id = R.string.scan_details_general_error_placeholder_content_descriptions
        ),
        label = stringResource(
            id = R.string.scan_details_general_error_placeholder_label
        ),
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ScanImageGallery(
    imageGalleryState: ScanImageGalleryState.Loaded,
    currentImageIndex: Int,
    onImageChange: (imageIndex: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(
        initialPage = currentImageIndex,
        pageCount = { imageGalleryState.images.size }
    )

    LaunchedEffect(pagerState.currentPage) {
        onImageChange(pagerState.currentPage)
    }

    HorizontalPager(state = pagerState, modifier = modifier) { page ->
        AsyncImage(
            model = imageGalleryState.images[page],
            contentDescription = stringResource(
                id = TranslationsR.string.scan_image_gallery_image_content_descriptions,
                pagerState.currentPage
            ),
            modifier = Modifier
                .fillMaxSize()
                .zoomable(rememberZoomState()),
            contentScale = ContentScale.FillWidth
        )
    }
}
