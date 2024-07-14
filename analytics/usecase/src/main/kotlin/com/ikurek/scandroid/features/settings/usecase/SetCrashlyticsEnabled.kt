package com.ikurek.scandroid.features.settings.usecase

import com.ikurek.scandroid.features.settings.data.repository.AnalyticsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetCrashlyticsEnabled @Inject internal constructor(
    private val analyticsRepository: AnalyticsRepository

) {
    suspend operator fun invoke(enabled: Boolean) =
        analyticsRepository.setCrashlyticsEnabled(enabled)
}
