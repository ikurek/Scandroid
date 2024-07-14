package com.ikurek.scandroid.features.scandetails.usecase

import android.util.Log
import com.ikurek.scandroid.analytics.ErrorTracker
import com.ikurek.scandroid.core.platform.Platform
import java.io.File
import javax.inject.Inject

class OpenPdfFile @Inject internal constructor(
    private val platform: Platform,
    private val errorTracker: ErrorTracker
) {
    suspend operator fun invoke(pdfFile: File): Result<Unit> =
        platform.openPdfFileOutside(pdfFile).onFailure { exception ->
            Log.d(this::class.simpleName, "Failed to open PDF file outside: $exception")
            errorTracker.trackNonFatal(exception, "Failed to open PDF file outside")
        }
}
