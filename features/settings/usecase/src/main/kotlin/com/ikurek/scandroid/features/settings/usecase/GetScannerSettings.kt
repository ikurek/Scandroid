package com.ikurek.scandroid.features.settings.usecase

import com.ikurek.scandroid.features.settings.data.model.ScannerSettings
import com.ikurek.scandroid.features.settings.data.repository.ScannerSettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetScannerSettings @Inject internal constructor(
    private val scannerSettingsRepository: ScannerSettingsRepository
) {
    suspend operator fun invoke() = ScannerSettings(
        scannerMode = scannerSettingsRepository.getScannerMode(),
        scannerFormats = scannerSettingsRepository.getScannerFormats()
    )
}
