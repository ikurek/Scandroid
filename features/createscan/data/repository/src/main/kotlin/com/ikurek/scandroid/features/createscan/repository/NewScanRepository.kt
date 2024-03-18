package com.ikurek.scandroid.features.createscan.repository

import android.util.Log
import com.ikurek.scandroid.core.filestore.FileFormat
import com.ikurek.scandroid.core.filestore.FilenameProvider
import com.ikurek.scandroid.core.filestore.directoryprovider.DirectoryProvider
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewScanRepository @Inject internal constructor(
    private val directoryProvider: DirectoryProvider,
    private val filenameProvider: FilenameProvider
) {

    fun saveScanPdfFileToStorage(scanId: String, inputFile: File): File {
        val scanDirectory = directoryProvider.getScanDirectory(scanId)
        val outputFileName = filenameProvider.createIndexedFilename(FileFormat.PDF)
        val outputFilePath = scanDirectory.path + "/" + outputFileName
        val outputFile = File(outputFilePath)
        Log.d(this::class.simpleName, "Copying PDF file ${inputFile.path} to ${outputFile.path}")
        return inputFile.copyTo(outputFile)
    }

    fun saveScanJpegFilesToStorage(scanId: String, inputFiles: List<File>): List<File> {
        val scanDirectory = directoryProvider.getScanDirectory(scanId)
        return inputFiles.mapIndexed { index, inputFile ->
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

    fun deleteScanFiles(scanId: String) {
        directoryProvider.getScanDirectory(scanId).deleteRecursively()
    }
}
