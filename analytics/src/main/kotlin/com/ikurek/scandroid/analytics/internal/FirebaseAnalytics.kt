package com.ikurek.scandroid.analytics.internal

import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.perf.FirebasePerformance
import com.ikurek.scandroid.analytics.Analytics
import com.ikurek.scandroid.analytics.data.model.AnalyticsStatus
import com.ikurek.scandroid.common.coroutines.di.AppCoroutineScope
import com.ikurek.scandroid.features.settings.usecase.ObserveAnalyticsStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class FirebaseAnalytics @Inject constructor(
    @AppCoroutineScope private val appCoroutineScope: CoroutineScope,
    private val observeAnalyticsStatus: ObserveAnalyticsStatus,
    private val firebaseAnalytics: FirebaseAnalytics,
    private val firebasePerformance: FirebasePerformance,
    private val firebaseCrashlytics: FirebaseCrashlytics
) : Analytics {

    override fun initialize() {
        appCoroutineScope.launch {
            observeAnalyticsStatus().collect(::updateAnalyticsStatus)
        }
    }

    private fun updateAnalyticsStatus(analyticsStatus: AnalyticsStatus) {
        Log.d(this::class.simpleName, "Updated analytics status: $analyticsStatus")
        firebaseAnalytics.setAnalyticsCollectionEnabled(analyticsStatus.areAnalyticsEnabled)
        firebaseCrashlytics.setCrashlyticsCollectionEnabled(analyticsStatus.areCrashlyticsEnabled)
        firebasePerformance.isPerformanceCollectionEnabled =
            analyticsStatus.isPerformanceMonitoringEnabled
    }
}
