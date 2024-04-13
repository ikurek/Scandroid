package com.ikurek.scandroid.features.savedscans.usecase

import com.ikurek.scandroid.features.savedscans.data.repository.SavedScansRepository
import javax.inject.Inject

class GetAllValidSavedScans @Inject internal constructor(
    private val scansRepository: SavedScansRepository
) {

    suspend operator fun invoke() = runCatching {
        scansRepository.getAllValidSavedScans()
    }
}
