package com.ikurek.scandroid.features.createscan.data.repository

import android.util.Log
import com.ikurek.scandroid.common.coroutines.IoDispatcher
import com.ikurek.scandroid.core.database.ScansDatabase
import com.ikurek.scandroid.core.filestore.FileFormat
import com.ikurek.scandroid.core.filestore.FilenameProvider
import com.ikurek.scandroid.core.filestore.directoryprovider.DirectoryProvider
import com.ikurek.scandroid.features.createscan.data.model.NewScan
import com.ikurek.scandroid.features.createscan.data.repository.mapper.toEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

private const val PdfScanFilename = "scan"

@Singleton
class NewScanRepository @Inject internal constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val directoryProvider: DirectoryProvider,
    private val filenameProvider: FilenameProvider,
    private val scansDatabase: ScansDatabase
) {

    suspend fun saveScanPdfFileToStorage(
        scanId: UUID,
        inputFile: File
    ): File = withContext(ioDispatcher) {
        val scanDirectory = directoryProvider.getScanDirectory(scanId)
        val outputFileName = filenameProvider.createFilename(FileFormat.PDF, PdfScanFilename)
        val outputFilePath = scanDirectory.path + "/" + outputFileName
        val outputFile = File(outputFilePath)
        Log.d(
            this::class.simpleName,
            "Copying PDF file ${inputFile.path} to ${outputFile.path}"
        )
        return@withContext inputFile.copyTo(outputFile)
    }

    suspend fun saveScanJpegFilesToStorage(
        scanId: UUID,
        inputFiles: List<File>
    ): List<File> = withContext(ioDispatcher) {
        val scanDirectory = directoryProvider.getScanDirectory(scanId)
        return@withContext inputFiles.mapIndexed { index, inputFile ->
            val outputFileName =
                filenameProvider.createIndexedFilename(FileFormat.JPEG, index)
            val outputFilePath = scanDirectory.path + "/" + outputFileName
            val outputFile = File(outputFilePath)
            Log.d(
                this::class.simpleName,
                "Copying JPEG file ${inputFile.path} to ${outputFile.path}"
            )
            inputFile.copyTo(outputFile)
        }
    }

    suspend fun saveNewScan(scan: NewScan) = withContext(ioDispatcher) {
        scansDatabase.save(scan.toEntity())
    }

    suspend fun deleteScanFiles(scanId: UUID) = withContext(ioDispatcher) {
        directoryProvider.getScanDirectory(scanId).deleteRecursively()
    }
}
