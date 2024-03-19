package com.ikurek.scandroid.features.createscan.usecase

import com.ikurek.scandroid.features.createscan.data.model.ScannedDocuments
import com.ikurek.scandroid.features.createscan.data.repository.LatestScanRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoreLatestUnsavedScan @Inject internal constructor(
    private val latestScanRepository: LatestScanRepository
) {

    operator fun invoke(scannedDocuments: ScannedDocuments) =
        latestScanRepository.store(scannedDocuments)
}
