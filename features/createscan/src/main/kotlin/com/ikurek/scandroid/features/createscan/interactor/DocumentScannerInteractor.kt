package com.ikurek.scandroid.features.createscan.interactor

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import com.google.mlkit.common.MlKitException
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import com.ikurek.scandroid.features.createscan.data.model.ScannedDocuments
import com.ikurek.scandroid.features.createscan.data.model.ScannerSettings
import com.ikurek.scandroid.features.createscan.data.model.exception.ScannerInitializationException
import com.ikurek.scandroid.features.createscan.data.model.exception.ScanningCancelled
import com.ikurek.scandroid.features.createscan.data.model.exception.SdkInitializationException
import com.ikurek.scandroid.features.createscan.data.model.exception.UnexpectedScanningError
import com.ikurek.scandroid.features.createscan.mapper.toGmsDocumentScannerOptions
import com.ikurek.scandroid.features.createscan.mapper.toScannedDocuments
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/*
 * This is a terribly, terribly designed API
 *
 * getStartScanIntent() does not even return na intent - it returns and intent sender which later
 * has to be converted to an intent server request, so it's not possible to wrap this in a custom
 * ActivityResultContract implementation (i tried)
 *
 * getStartScanIntent() also requires an entire Activity to work, which I fail to understand since
 * when I peeked at the compiled code for this method it only uses the activity to call
 * activity.getApplicationContext() so why not just let me pass the fucking context I can inject
 * with @ApplicationContext???
 *
 * Also, the method ds are async based on Task class from gms.Task so it's not possible to run this
 * in a nice way, so I used the coroutines bridge for task
 *
 * All of those make it literally impossible to separate this from the Activity or platform in any
 * way without 12312351 layers of abstraction, so I just gave up, fuck this
 *
 * FIXME: Fix this somehow so it does not have to be injected into activity
 */

@ActivityScoped
class DocumentScannerInteractor @Inject internal constructor(
    private val activity: Activity
) {

    suspend fun createRequest(
        scannerSettings: ScannerSettings
    ): Result<IntentSenderRequest> = runCatching {
        val options = scannerSettings.toGmsDocumentScannerOptions()
        val scanner = GmsDocumentScanning.getClient(options)
        val intentSender = scanner.getStartScanIntent(activity).await()
        val intentSenderRequest = IntentSenderRequest.Builder(intentSender).build()
        return@runCatching intentSenderRequest
    }.recoverCatching { error ->
        Log.e(this::class.simpleName, "Failed to initialize scanner", error)
        throw when (error) {
            is MlKitException -> SdkInitializationException(
                "MlKit error ${error.errorCode}",
                error
            )

            else -> ScannerInitializationException(
                "Barcode scanner initialization failed",
                error
            )
        }
    }

    fun parseResult(
        documentScanResult: ActivityResult
    ): Result<ScannedDocuments> = when (documentScanResult.resultCode) {
        Activity.RESULT_OK -> handleResultOk(documentScanResult.data)
        Activity.RESULT_CANCELED -> Result.failure(ScanningCancelled())
        else -> Result.failure(UnexpectedScanningError(message = "Invalid activity result code"))
    }.onFailure { error ->
        Log.e(this::class.simpleName, "Failed to read scanner result", error)
    }

    private fun handleResultOk(intent: Intent?): Result<ScannedDocuments> = runCatching {
        val result = GmsDocumentScanningResult.fromActivityResultIntent(intent)
        requireNotNull(result) { "Result from scanner should not be null" }
        val scannedDocuments = result.toScannedDocuments()
        return Result.success(scannedDocuments)
    }
}
