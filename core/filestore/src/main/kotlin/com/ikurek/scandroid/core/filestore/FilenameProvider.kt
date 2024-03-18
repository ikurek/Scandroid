package com.ikurek.scandroid.core.filestore

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilenameProvider @Inject internal constructor() {

    fun createIndexedFilename(
        fileFormat: FileFormat,
        index: Int = 0
    ) = index.toString() + "." + fileFormat.extension
}
