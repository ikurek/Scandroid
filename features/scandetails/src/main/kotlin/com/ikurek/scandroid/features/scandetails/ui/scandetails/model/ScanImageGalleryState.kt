package com.ikurek.scandroid.features.scandetails.ui.scandetails.model

import java.io.File

sealed interface ScanImageGalleryState {
    data object Loading : ScanImageGalleryState

    data class Loaded(val images: List<File>) : ScanImageGalleryState

    data object Error : ScanImageGalleryState
}
