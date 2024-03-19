package com.ikurek.scandroid.features.createscan.usecase

import com.ikurek.scandroid.features.createscan.data.repository.LatestScanRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetLatestUnsavedScan @Inject internal constructor(
    private val latestScanRepository: LatestScanRepository
) {

    operator fun invoke() = latestScanRepository.read()
}
