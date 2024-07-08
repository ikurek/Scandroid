package com.ikurek.scandroid.features.settings.usecase

import com.ikurek.scandroid.features.settings.data.model.ScannerMode
import com.ikurek.scandroid.features.settings.data.repository.ScannerSettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetScannerMode @Inject internal constructor(
    private val scannerSettingsRepository: ScannerSettingsRepository
) {
    suspend operator fun invoke(scannerMode: ScannerMode) =
        scannerSettingsRepository.setScannerMode(scannerMode)
}
