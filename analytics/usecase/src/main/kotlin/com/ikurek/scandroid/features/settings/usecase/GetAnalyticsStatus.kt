package com.ikurek.scandroid.features.settings.usecase

import com.ikurek.scandroid.analytics.data.model.AnalyticsStatus
import com.ikurek.scandroid.features.settings.data.repository.AnalyticsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAnalyticsStatus @Inject internal constructor(
    private val analyticsRepository: AnalyticsRepository
) {

    suspend operator fun invoke() = AnalyticsStatus(
        areAnalyticsEnabled = analyticsRepository.areAnalyticsEnabled(),
        areCrashlyticsEnabled = analyticsRepository.areCrashlyticsEnabled(),
        isPerformanceMonitoringEnabled = analyticsRepository.isPerformanceMonitoringEnabled()
    )
}
