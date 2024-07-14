package com.ikurek.scandroid.analytics

interface ErrorTracker {
    fun trackNonFatal(exception: Throwable, message: String? = null)
}
