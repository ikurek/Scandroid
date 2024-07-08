package com.ikurek.scandroid.features.settings.usecase

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetCrashlyticsEnabled @Inject internal constructor() {
    suspend operator fun invoke(enabled: Boolean) {
        // TODO: Implement
    }
}
