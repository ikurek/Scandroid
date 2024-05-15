package com.ikurek.scandroid.features.scandetails.ui.scanimagegallery

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikurek.scandroid.features.savedscans.usecase.GetSavedScanImages
import com.ikurek.scandroid.features.scandetails.ScanImageGalleryScreenArgs
import com.ikurek.scandroid.features.scandetails.ui.scandetails.model.ScanImageGalleryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ScanImageGalleryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSavedScanImages: GetSavedScanImages
) : ViewModel() {

    private val args: ScanImageGalleryScreenArgs = ScanImageGalleryScreenArgs(savedStateHandle)

    private val _currentImageIndex: MutableStateFlow<Int> = MutableStateFlow(args.initialImageIndex)
    val currentImageIndex: StateFlow<Int> = _currentImageIndex

    private val _imageGalleryState: MutableStateFlow<ScanImageGalleryState> =
        MutableStateFlow(ScanImageGalleryState.Loading)
    val imageGalleryState: StateFlow<ScanImageGalleryState> = _imageGalleryState

    fun onScreenEnter() = viewModelScope.launch {
        getSavedScanImages(args.scanId).onSuccess {
            _imageGalleryState.value = ScanImageGalleryState.Loaded(images = it.imageFiles)
        }.onFailure {
            _imageGalleryState.value = ScanImageGalleryState.Error
        }
    }

    fun onImageChange(imageIndex: Int) {
        _currentImageIndex.value = imageIndex
    }
}
