package com.ikurek.scandroid.features.settings.data.repository

import com.ikurek.scandroid.core.datastore.Storage
import com.ikurek.scandroid.core.datastore.di.SettingsStorage
import com.ikurek.scandroid.features.settings.data.model.ScannerFormats
import com.ikurek.scandroid.features.settings.data.model.ScannerMode
import javax.inject.Inject
import javax.inject.Singleton

const val ScannerModeKey = "scanner_mode"
const val ScannerFormatsKey = "scanner_formats"

@Singleton
class ScannerSettingsRepository @Inject internal constructor(
    @SettingsStorage private val storage: Storage
) {

    suspend fun setScannerMode(scannerMode: ScannerMode) =
        storage.putString(ScannerModeKey, scannerMode.value)

    suspend fun getScannerMode(): ScannerMode =
        storage.getString(ScannerModeKey)
            ?.let { ScannerMode.valueOrDefault(it) }
            ?: ScannerMode.Default

    suspend fun setScannerFormats(scannerFormats: ScannerFormats) =
        storage.putString(ScannerFormatsKey, scannerFormats.value)

    suspend fun getScannerFormats(): ScannerFormats =
        storage.getString(ScannerFormatsKey)
            ?.let { ScannerFormats.fromValue(it) }
            ?: ScannerFormats.Default
}
