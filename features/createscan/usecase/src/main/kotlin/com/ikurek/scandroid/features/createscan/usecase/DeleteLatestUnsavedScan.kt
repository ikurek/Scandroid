package com.ikurek.scandroid.features.createscan.usecase

import com.ikurek.scandroid.features.createscan.repository.LatestScanRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteLatestUnsavedScan @Inject internal constructor(
    private val latestScanRepository: LatestScanRepository
) {

    operator fun invoke() = latestScanRepository.clear()
}
