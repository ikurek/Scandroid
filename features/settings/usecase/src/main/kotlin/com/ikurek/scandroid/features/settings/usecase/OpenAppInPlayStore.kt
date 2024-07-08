package com.ikurek.scandroid.features.settings.usecase

import com.ikurek.scandroid.core.platform.Platform
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OpenAppInPlayStore @Inject internal constructor(
    private val platform: Platform
) {
    operator fun invoke() = platform.openAppInAppStore()
}
