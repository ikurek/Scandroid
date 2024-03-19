package com.ikurek.scandroid.features.createscan.data.repository

import com.ikurek.scandroid.features.createscan.data.model.ScannedDocuments
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LatestScanRepository @Inject internal constructor() {

    private var scannedDocuments: ScannedDocuments? = null

    fun store(scannedDocuments: ScannedDocuments) {
        this.scannedDocuments = scannedDocuments
    }

    fun read(): ScannedDocuments? {
        return scannedDocuments
    }

    fun clear() {
        this.scannedDocuments = null
    }
}
