package com.ikurek.scandroid.features.createscan.usecase

import android.util.Log
import androidx.core.net.toFile
import com.ikurek.scandroid.analytics.ErrorTracker
import com.ikurek.scandroid.features.createscan.data.model.NewScan
import com.ikurek.scandroid.features.createscan.data.model.ScanFileFormat
import com.ikurek.scandroid.features.createscan.data.model.ScannedDocuments
import com.ikurek.scandroid.features.createscan.data.repository.NewScanRepository
import java.util.UUID
import javax.inject.Inject

class SaveScannedDocuments @Inject internal constructor(
    private val newScanRepository: NewScanRepository,
    private val errorTracker: ErrorTracker
) {

    @Suppress("MagicNumber")
    suspend operator fun invoke(
        name: String,
        description: String,
        scannedDocuments: ScannedDocuments,
        selectedFileFormats: Set<ScanFileFormat>
    ): Result<UUID> {
        val scanId: UUID = UUID.randomUUID()

        return runCatching {
            assert(scannedDocuments.isEmpty.not()) { "Either PDF or JPEG file URIs are required" }
            assert(selectedFileFormats.isNotEmpty()) { "At least one file format has to be selected" }

            if (canSavePdfFile(scannedDocuments, selectedFileFormats)) {
                newScanRepository.saveScanPdfFileToStorage(
                    scanId = scanId,
                    inputFile = scannedDocuments.pdfUri!!.toFile()
                )
            }

            if (canSaveJpegFiles(scannedDocuments, selectedFileFormats)) {
                newScanRepository.saveScanJpegFilesToStorage(
                    scanId = scanId,
                    inputFiles = scannedDocuments.imageUris.map { it.toFile() }
                )
            }

            newScanRepository.saveNewScan(
                NewScan(
                    id = scanId,
                    createdAt = scannedDocuments.createdAt,
                    name = name,
                    description = description
                )
            )

            return Result.success(scanId)
        }.onFailure {
            Log.e(this::class.simpleName, "Failed to save scan $scanId files", it)
            errorTracker.trackNonFatal(it, "Failed to save scan files")
            newScanRepository.deleteScanFiles(scanId)
            Log.d(this::class.simpleName, "Scan $scanId deleted from storage")
        }
    }

    private fun canSavePdfFile(
        scannedDocuments: ScannedDocuments,
        selectedFileFormats: Set<ScanFileFormat>
    ) = selectedFileFormats.contains(ScanFileFormat.PDF) && scannedDocuments.pdfUri != null

    private fun canSaveJpegFiles(
        scannedDocuments: ScannedDocuments,
        selectedFileFormats: Set<ScanFileFormat>
    ) =
        selectedFileFormats.contains(ScanFileFormat.JPEG) && scannedDocuments.imageUris.isNotEmpty()
}
