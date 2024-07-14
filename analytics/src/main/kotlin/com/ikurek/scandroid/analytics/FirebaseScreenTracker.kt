package com.ikurek.scandroid.analytics

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class FirebaseScreenTracker @Inject constructor(
    private val screenResolver: ScreenResolver
) : ScreenTracker {

    override fun trackScreenView(route: String) {
        screenResolver.resolveRoute(route).onSuccess {
            // Track screen in Firebase
        }.onFailure {
            // Log error to analytics
        }
    }
}
