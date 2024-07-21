package com.ikurek.scandroid.core.platform

import java.io.File

interface Platform {

    fun openPdfFileOutside(file: File): Result<Unit>

    fun sharePdfFile(file: File): Result<Unit>

    fun openImageFilesOutside(files: List<File>): Result<Unit>

    fun shareImageFiles(files: List<File>): Result<Unit>

    fun openAppInAppStore(): Result<Unit>

    fun openOssLicenses(): Result<Unit>
}
