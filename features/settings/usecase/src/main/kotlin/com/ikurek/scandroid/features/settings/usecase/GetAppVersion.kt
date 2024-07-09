package com.ikurek.scandroid.features.settings.usecase

import com.ikurek.scandroid.core.platform.AppData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAppVersion @Inject internal constructor(
    private val appData: AppData
) {

    operator fun invoke() = "${appData.versionName} (${appData.versionCode})"
}
