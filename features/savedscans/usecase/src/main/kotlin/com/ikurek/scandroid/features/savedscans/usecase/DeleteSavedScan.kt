package com.ikurek.scandroid.features.savedscans.usecase

import com.ikurek.scandroid.analytics.ErrorTracker
import com.ikurek.scandroid.features.savedscans.data.repository.SavedScansRepository
import java.util.UUID
import javax.inject.Inject

class DeleteSavedScan @Inject internal constructor(
    private val scansRepository: SavedScansRepository,
    private val errorTracker: ErrorTracker
) {

    suspend operator fun invoke(id: UUID) = runCatching {
        scansRepository.deleteScan(id)
    }.onFailure { error ->
        errorTracker.trackNonFatal(error, "Failed to delete scan")
    }
}
