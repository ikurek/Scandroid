package com.ikurek.scandroid.analytics.internal

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.ikurek.scandroid.analytics.ErrorTracker
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class FirebaseErrorTracker @Inject constructor(
    private val firebaseCrashlytics: FirebaseCrashlytics
) : ErrorTracker {

    override fun trackNonFatal(exception: Throwable, message: String?,) {
        message?.let { firebaseCrashlytics.log(message) }
        firebaseCrashlytics.recordException(exception)
    }
}
