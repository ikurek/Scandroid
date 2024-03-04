package com.ikurek.scandroid.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikurek.scandroid.features.createscan.model.ScannedDocuments
import com.ikurek.scandroid.features.createscan.model.ScannerSettings
import com.ikurek.scandroid.features.createscan.model.exception.ScannerInitializationException
import com.ikurek.scandroid.features.createscan.model.exception.ScanningCancelled
import com.ikurek.scandroid.features.createscan.model.exception.SdkInitializationException
import com.ikurek.scandroid.features.createscan.model.exception.UnexpectedScanningError
import com.ikurek.scandroid.features.createscan.usecase.DeleteLatestUnsavedScan
import com.ikurek.scandroid.features.createscan.usecase.StoreLatestUnsavedScan
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val deleteLatestUnsavedScan: DeleteLatestUnsavedScan,
    private val storeLatestUnsavedScan: StoreLatestUnsavedScan
) : ViewModel() {

    private val _infoDialog: MutableStateFlow<HomeInfoDialog?> = MutableStateFlow(null)
    val infoDialog: StateFlow<HomeInfoDialog?> = _infoDialog

    private val _sideEffects: Channel<HomeSideEffect> = Channel()
    val sideEffects: Flow<HomeSideEffect> = _sideEffects.receiveAsFlow()

    fun onCreateScanClick() = viewModelScope.launch {
        deleteLatestUnsavedScan()
        _sideEffects.send(HomeSideEffect.StartDocumentScanner(ScannerSettings.Default))
    }

    fun onScannerFinished(result: Result<ScannedDocuments>) = result
        .onSuccess(::onScannerResultReadSuccess)
        .onFailure(::onScannerResultReadFailed)

    private fun onScannerResultReadSuccess(
        scannedDocuments: ScannedDocuments
    ) = viewModelScope.launch {
        storeLatestUnsavedScan(scannedDocuments)
        _sideEffects.send(HomeSideEffect.OpenNewScanScreen)
    }

    private fun onScannerResultReadFailed(error: Throwable) = when (error) {
        is ScanningCancelled -> Unit
        is UnexpectedScanningError -> _infoDialog.value = HomeInfoDialog.ScannerResultReadFailed
        else -> error(error)
    }

    fun onScannerInitializationFailed(error: Throwable) {
        _infoDialog.value = when (error) {
            is SdkInitializationException -> HomeInfoDialog.SdkInitializationFailed
            is ScannerInitializationException -> HomeInfoDialog.ScannerInitializationFailed
            else -> error(error)
        }
    }

    fun onInfoDialogDismissed() {
        _infoDialog.value = null
    }
}
