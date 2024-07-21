package com.ikurek.scandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikurek.scandroid.analytics.ScreenTracker
import com.ikurek.scandroid.features.createscan.data.model.ScannedDocuments
import com.ikurek.scandroid.features.createscan.data.model.exception.ScannerInitializationException
import com.ikurek.scandroid.features.createscan.data.model.exception.ScanningCancelled
import com.ikurek.scandroid.features.createscan.data.model.exception.SdkInitializationException
import com.ikurek.scandroid.features.createscan.data.model.exception.UnexpectedScanningError
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
internal class MainViewModel @Inject constructor(
    private val deleteLatestUnsavedScan: DeleteLatestUnsavedScan,
    private val storeLatestUnsavedScan: StoreLatestUnsavedScan,
    private val screenTracker: ScreenTracker
) : ViewModel() {

    private val _dialog: MutableStateFlow<MainDialog?> = MutableStateFlow(null)
    val dialog: StateFlow<MainDialog?> = _dialog

    private val _sideEffects: Channel<MainSideEffect> = Channel()
    val sideEffects: Flow<MainSideEffect> = _sideEffects.receiveAsFlow()

    fun onCreateScanClick() = viewModelScope.launch {
        deleteLatestUnsavedScan()
        _sideEffects.send(MainSideEffect.StartDocumentScanner)
    }

    fun onScannerFinished(result: Result<ScannedDocuments>) = result
        .onSuccess(::onScannerResultReadSuccess)
        .onFailure(::onScannerResultReadFailed)

    private fun onScannerResultReadSuccess(
        scannedDocuments: ScannedDocuments
    ) = viewModelScope.launch {
        storeLatestUnsavedScan(scannedDocuments)
        _sideEffects.send(MainSideEffect.OpenNewScanScreen)
    }

    private fun onScannerResultReadFailed(error: Throwable) = when (error) {
        is ScanningCancelled -> Unit
        is UnexpectedScanningError ->
            _dialog.value = MainDialog.Error.ScannerResultReadFailed(error)

        else -> error(error)
    }

    fun onScannerInitializationFailed(error: Throwable) {
        _dialog.value = when (error) {
            is SdkInitializationException -> MainDialog.Error.SdkInitializationFailedDialog(error)
            is ScannerInitializationException -> MainDialog.Error.ScannerInitializationFailed(error)
            else -> error(error)
        }
    }

    fun onDialogDismissed() {
        _dialog.value = null
    }

    fun onDestinationChanged(route: String) {
        screenTracker.trackScreenView(route)
    }
}
