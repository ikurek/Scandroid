package com.ikurek.scandroid.features.savedscans.data.repository

import com.ikurek.scandroid.common.coroutines.IoDispatcher
import com.ikurek.scandroid.core.database.ScansDatabase
import com.ikurek.scandroid.core.filestore.FileFormat
import com.ikurek.scandroid.core.filestore.directoryprovider.DirectoryProvider
import com.ikurek.scandroid.features.savedscans.data.model.SavedScan
import com.ikurek.scandroid.features.savedscans.data.model.SavedScanFiles
import com.ikurek.scandroid.features.savedscans.data.repository.mapper.toSavedScan
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SavedScansRepository @Inject internal constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val directoryProvider: DirectoryProvider,
    private val scansDatabase: ScansDatabase
) {

    suspend fun getAllValidSavedScans(): List<SavedScan> = withContext(ioDispatcher) {
        scansDatabase.findAll().mapNotNull { entity ->
            readScanFiles(entity.id).getOrNull()?.let { scanFiles ->
                entity.toSavedScan(scanFiles)
            }
        }
    }

    suspend fun getSavedScan(id: UUID): SavedScan = withContext(ioDispatcher) {
        val entity = scansDatabase.findById(id)
        requireNotNull(entity) { "Could not find scan with ID $id in database" }
        val files = readScanFiles(id).getOrNull()
        requireNotNull(files) { "Could not find valid files for scan with ID $id" }
        entity.toSavedScan(files)
    }

    private fun readScanFiles(id: UUID): Result<SavedScanFiles> {
        val files = directoryProvider.getScanDirectory(id).listFiles()
        requireNotNull(files) { "Could not list files for scan with ID $id" }
        require(files.isNotEmpty()) { "Could not find files for scan with ID $id" }

        val imageFiles = files.filter { it.extension == FileFormat.JPEG.extension }
        val pdfFile = files.find { it.extension == FileFormat.PDF.extension }
        return SavedScanFiles(pdfFile, imageFiles)
    }

    suspend fun updateSavedScanAccessDateTime(id: UUID, accessDateTime: ZonedDateTime) =
        scansDatabase.updateLastAccessedAt(id, accessDateTime)
}
