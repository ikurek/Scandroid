package com.ikurek.scandroid.features.savedscans.usecase

import com.ikurek.scandroid.features.savedscans.data.repository.SavedScansRepository
import java.time.Clock
import java.time.ZonedDateTime
import java.util.UUID
import javax.inject.Inject

class MarkScanAsViewed @Inject internal constructor(
    private val clock: Clock,
    private val scansRepository: SavedScansRepository
) {

    suspend operator fun invoke(scanId: UUID) =
        scansRepository.updateSavedScanAccessDateTime(scanId, ZonedDateTime.now(clock))
}
