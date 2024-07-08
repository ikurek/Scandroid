package com.ikurek.scandroid.features.settings.usecase

import com.ikurek.scandroid.features.settings.data.model.ScannerFormats
import com.ikurek.scandroid.features.settings.data.repository.ScannerSettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetScannerFormats @Inject internal constructor(
    private val scannerSettingsRepository: ScannerSettingsRepository
) {
    suspend operator fun invoke(scannerFormats: ScannerFormats) =
        scannerSettingsRepository.setScannerFormats(scannerFormats)
}
