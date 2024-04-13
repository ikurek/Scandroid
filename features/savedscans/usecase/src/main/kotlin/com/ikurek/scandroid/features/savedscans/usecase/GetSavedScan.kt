package com.ikurek.scandroid.features.savedscans.usecase

import com.ikurek.scandroid.features.savedscans.data.repository.SavedScansRepository
import java.util.UUID
import javax.inject.Inject

class GetSavedScan @Inject internal constructor(
    private val scansRepository: SavedScansRepository
) {

    suspend operator fun invoke(id: UUID) = runCatching {
        scansRepository.getSavedScan(id)
    }
}
