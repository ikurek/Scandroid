package com.ikurek.scandroid.features.createscan.interactor

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import com.ikurek.scandroid.features.createscan.mapper.toGmsDocumentScannerOptions
import com.ikurek.scandroid.features.createscan.mapper.toScannedDocuments
import com.ikurek.scandroid.features.createscan.model.ScannedDocuments
import com.ikurek.scandroid.features.createscan.model.ScannerSettings
import com.ikurek.scandroid.features.createscan.model.exception.ScannerInitializationException
import com.ikurek.scandroid.features.createscan.model.exception.ScanningCancelled
import com.ikurek.scandroid.features.createscan.model.exception.UnexpectedScanningError
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
    }.recoverCatching { exception: Throwable ->
        throw ScannerInitializationException(
            "Barcode scanner initialization failed",
            exception
        )
    }

    fun parseResult(
        documentScanResult: ActivityResult
    ): Result<ScannedDocuments> = when (documentScanResult.resultCode) {
        Activity.RESULT_OK -> handleResultOk(documentScanResult.data)
        Activity.RESULT_CANCELED -> Result.failure(ScanningCancelled())
        else -> Result.failure(UnexpectedScanningError(message = "Invalid activity result code"))
    }

    private fun handleResultOk(intent: Intent?): Result<ScannedDocuments> = runCatching {
        val result = GmsDocumentScanningResult.fromActivityResultIntent(intent)
        requireNotNull(result) { "Result from scanner should not be null" }
        val scannedDocuments = result.toScannedDocuments()
        return Result.success(scannedDocuments)
    }
}