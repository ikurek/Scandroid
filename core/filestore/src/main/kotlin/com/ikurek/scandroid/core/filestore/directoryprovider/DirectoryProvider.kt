package com.ikurek.scandroid.core.filestore.directoryprovider

import java.io.File
import java.util.UUID

interface DirectoryProvider {

    fun getScansDirectory(): File

    fun getScanDirectory(id: UUID): File
}
