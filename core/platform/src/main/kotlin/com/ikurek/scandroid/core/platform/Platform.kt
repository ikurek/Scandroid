package com.ikurek.scandroid.core.platform

import java.io.File

interface Platform {

    fun openPdfFileOutside(file: File): Result<Unit>

    fun sharePdfFile(file: File): Result<Unit>

    fun shareImageFiles(files: List<File>): Result<Unit>
}
