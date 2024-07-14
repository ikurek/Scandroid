package com.ikurek.scandroid.analytics.internal

import com.ikurek.scandroid.analytics.data.model.AnalyticsException
import com.ikurek.scandroid.analytics.data.model.Screen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ScreenResolver @Inject constructor() {

    fun resolveRoute(route: String): Result<Screen> {
        val screen = when (route) {
            "about" -> Screen.About
            "new-scan" -> Screen.NewScan
            "saved-scans" -> Screen.SavedScans
            "scan-details/{scanId}" -> Screen.ScanDetails
            "scan-details/{scanId}/images/{initialImageIndex}" -> Screen.ScanImageGallery
            "settings" -> Screen.Settings
            else -> null
        }

        return if (screen != null) {
            Result.success(screen)
        } else {
            Result.failure(AnalyticsException("Unable to track route: $route"))
        }
    }
}
