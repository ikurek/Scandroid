package com.ikurek.scandroid.features.search.usecase

import com.ikurek.scandroid.features.savedscans.data.repository.SavedScansRepository
import javax.inject.Inject

class SearchScans @Inject internal constructor(
    private val scansRepository: SavedScansRepository
) {

    suspend operator fun invoke(query: String) =
        scansRepository.searchSavedScans(query)
}
