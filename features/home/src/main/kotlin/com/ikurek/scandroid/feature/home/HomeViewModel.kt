package com.ikurek.scandroid.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikurek.scandroid.features.createscan.model.ScannedDocuments
import com.ikurek.scandroid.features.createscan.model.ScannerSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor() : ViewModel() {

    private val _sideEffects: Channel<HomeSideEffect> = Channel()
    val sideEffects: Flow<HomeSideEffect> = _sideEffects.receiveAsFlow()

    fun onCreateScanClick() = viewModelScope.launch {
        _sideEffects.send(HomeSideEffect.StartDocumentScanner(ScannerSettings.Default))
    }

    @Suppress("UnusedParameter")
    fun onScannerFinished(result: Result<ScannedDocuments>) {
        // TODO
    }

    @Suppress("UnusedParameter")
    fun onScannerInitializationFailed(exception: Throwable) {
        // TODO
    }
}
