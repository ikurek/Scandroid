package com.ikurek.scandroid.features.savedscans.usecase

import com.ikurek.scandroid.features.savedscans.data.model.SavedScanImages
import com.ikurek.scandroid.features.savedscans.data.repository.SavedScansRepository
import java.util.UUID
import javax.inject.Inject

class GetSavedScanImages @Inject internal constructor(
    private val scansRepository: SavedScansRepository
) {

    suspend operator fun invoke(id: UUID): Result<SavedScanImages> = runCatching {
        val files = scansRepository.getSavedScan(id).files.imageFiles
        check(!files.isNullOrEmpty()) { "Scan has no images" }
        return Result.success(SavedScanImages(files))
    }
}
