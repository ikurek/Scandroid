package com.ikurek.scandroid.features.settings.usecase

import com.ikurek.scandroid.analytics.data.model.AnalyticsStatus
import com.ikurek.scandroid.features.settings.data.repository.AnalyticsRepository
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ObserveAnalyticsStatus @Inject internal constructor(
    private val analyticsRepository: AnalyticsRepository
) {

    operator fun invoke() = combine(
        analyticsRepository.observeAnalyticsEnabled(),
        analyticsRepository.observeCrashlyticsEnabled(),
        analyticsRepository.observePerformanceMonitoringEnabled()
    ) { analyticsEnabled, crashlyticsEnabled, performanceMonitoringEnabled ->
        AnalyticsStatus(
            areAnalyticsEnabled = analyticsEnabled,
            areCrashlyticsEnabled = crashlyticsEnabled,
            isPerformanceMonitoringEnabled = performanceMonitoringEnabled
        )
    }
}
