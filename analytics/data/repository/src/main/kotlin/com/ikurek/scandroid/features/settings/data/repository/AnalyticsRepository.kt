package com.ikurek.scandroid.features.settings.data.repository

import com.ikurek.scandroid.core.datastore.Storage
import com.ikurek.scandroid.core.datastore.di.AnalyticsStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val AnalyticsEnabledKey = "analytics_enabled"
private const val CrashlyticsEnabledKey = "crashlytics_enabled"
private const val PerformanceMonitoringEnabledKey = "performance_monitoring_enabled"

@Singleton
class AnalyticsRepository @Inject internal constructor(
    @AnalyticsStorage private val storage: Storage
) {

    suspend fun setAnalyticsEnabled(enabled: Boolean) =
        storage.putBoolean(AnalyticsEnabledKey, enabled)

    suspend fun areAnalyticsEnabled(): Boolean =
        storage.getBoolean(AnalyticsEnabledKey) ?: true

    fun observeAnalyticsEnabled(): Flow<Boolean> =
        storage.observeBoolean(AnalyticsEnabledKey).map { it ?: true }

    suspend fun setCrashlyticsEnabled(enabled: Boolean) =
        storage.putBoolean(CrashlyticsEnabledKey, enabled)

    suspend fun areCrashlyticsEnabled(): Boolean =
        storage.getBoolean(CrashlyticsEnabledKey) ?: true

    fun observeCrashlyticsEnabled(): Flow<Boolean> =
        storage.observeBoolean(CrashlyticsEnabledKey).map { it ?: true }

    suspend fun setPerformanceMonitoringEnabled(enabled: Boolean) =
        storage.putBoolean(PerformanceMonitoringEnabledKey, enabled)

    suspend fun isPerformanceMonitoringEnabled(): Boolean =
        storage.getBoolean(PerformanceMonitoringEnabledKey) ?: true

    fun observePerformanceMonitoringEnabled(): Flow<Boolean> =
        storage.observeBoolean(PerformanceMonitoringEnabledKey).map { it ?: true }
}
