package com.ikurek.scandroid.core.filestore.directoryprovider

import java.io.File

interface DirectoryProvider {

    fun getScansDirectory(): File

    fun getScanDirectory(id: String): File
}
