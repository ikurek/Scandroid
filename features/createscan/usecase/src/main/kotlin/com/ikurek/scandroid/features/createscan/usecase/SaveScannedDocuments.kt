package com.ikurek.scandroid.features.createscan.usecase

import android.util.Log
import androidx.core.net.toFile
import com.ikurek.scandroid.features.createscan.data.model.ScannedDocuments
import com.ikurek.scandroid.features.createscan.data.model.ScannerFileFormat
import com.ikurek.scandroid.features.createscan.data.repository.NewScanRepository
import java.util.UUID
import javax.inject.Inject

class SaveScannedDocuments @Inject internal constructor(
    private val newScanRepository: NewScanRepository
) {

    @Suppress("MagicNumber")
    suspend operator fun invoke(
        name: String,
        description: String,
        scannedDocuments: ScannedDocuments,
        selectedFileFormats: Set<ScannerFileFormat>
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

            return Result.success(scanId)
        }.onFailure {
            Log.e(this::class.simpleName, "Failed to save scan $scanId files", it)
            newScanRepository.deleteScanFiles(scanId)
            Log.d(this::class.simpleName, "Scan $scanId deleted from storage")
        }
    }

    private fun canSavePdfFile(
        scannedDocuments: ScannedDocuments,
        selectedFileFormats: Set<ScannerFileFormat>
    ) = selectedFileFormats.contains(ScannerFileFormat.PDF) && scannedDocuments.pdfUri != null

    private fun canSaveJpegFiles(
        scannedDocuments: ScannedDocuments,
        selectedFileFormats: Set<ScannerFileFormat>
    ) =
        selectedFileFormats.contains(ScannerFileFormat.JPEG) && scannedDocuments.imageUris.isNotEmpty()
}
