package com.ikurek.scandroid.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class FirebaseScreenTracker @Inject constructor(
    private val screenResolver: ScreenResolver,
    private val firebaseAnalytics: FirebaseAnalytics
) : ScreenTracker {

    override fun trackScreenView(route: String) {
        screenResolver.resolveRoute(route).onSuccess {
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
                param(FirebaseAnalytics.Param.SCREEN_NAME, it.screenName)
            }
        }.onFailure {
            // Log error to analytics
        }
    }
}
