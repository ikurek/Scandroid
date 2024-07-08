package com.ikurek.scandroid.features.settings.usecase

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetAnalyticsEnabled @Inject internal constructor() {
    suspend operator fun invoke(enabled: Boolean) {
        // TODO: Implement
    }
}
