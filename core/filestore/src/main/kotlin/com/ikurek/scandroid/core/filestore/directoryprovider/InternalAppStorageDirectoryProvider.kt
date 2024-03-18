package com.ikurek.scandroid.core.filestore.directoryprovider

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class InternalAppStorageDirectoryProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : DirectoryProvider {

    override fun getScansDirectory(): File {
        val path = context.filesDir.path + "/scans"
        return File(path).createIfNotExists()
    }

    override fun getScanDirectory(id: String): File {
        val path = getScansDirectory().path + "/$id"
        return File(path).createIfNotExists()
    }

    private fun File.createIfNotExists(): File {
        if (exists().not()) {
            Log.d(this::class.simpleName, "Creating directory $path")
            mkdirs()
        }
        return this
    }
}
