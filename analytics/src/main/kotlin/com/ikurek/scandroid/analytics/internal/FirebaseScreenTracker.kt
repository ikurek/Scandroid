package com.ikurek.scandroid.analytics.internal

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import com.ikurek.scandroid.analytics.ErrorTracker
import com.ikurek.scandroid.analytics.ScreenTracker
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class FirebaseScreenTracker @Inject constructor(
    private val screenResolver: ScreenResolver,
    private val firebaseAnalytics: FirebaseAnalytics,
    private val errorTracker: ErrorTracker
) : ScreenTracker {

    override fun trackScreenView(route: String) {
        screenResolver.resolveRoute(route).onSuccess {
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
                param(FirebaseAnalytics.Param.SCREEN_NAME, it.screenName)
            }
        }.onFailure {
            errorTracker.trackNonFatal(exception = it)
        }
    }
}
