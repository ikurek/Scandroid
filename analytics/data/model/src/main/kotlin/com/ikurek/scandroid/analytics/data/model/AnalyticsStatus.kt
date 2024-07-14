package com.ikurek.scandroid.analytics.data.model

data class AnalyticsStatus(
    val areAnalyticsEnabled: Boolean,
    val areCrashlyticsEnabled: Boolean,
    val isPerformanceMonitoringEnabled: Boolean
)
