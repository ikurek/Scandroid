package com.ikurek.scandroid.features.savedscans.data.model

import java.io.File

sealed interface SavedScanFiles {
    val pdfFile: File?
    val imageFiles: List<File>?

    data class PdfOnly(
        override val pdfFile: File
    ) : SavedScanFiles {
        override val imageFiles: List<File>? = null
    }

    data class ImagesOnly(
        override val imageFiles: List<File>
    ) : SavedScanFiles {
        override val pdfFile: File? = null
    }

    data class PdfAndImages(
        override val pdfFile: File,
        override val imageFiles: List<File>
    ) : SavedScanFiles

    companion object {

        operator fun invoke(
            pdfFile: File?,
            imageFiles: List<File>
        ): Result<SavedScanFiles> = runCatching {
            val files: SavedScanFiles = when {
                pdfFile != null && imageFiles.isNotEmpty() -> PdfAndImages(pdfFile, imageFiles)
                pdfFile != null -> PdfOnly(pdfFile)
                imageFiles.isNotEmpty() -> ImagesOnly(imageFiles)
                else -> error("Either PDF or image files are required")
            }

            return Result.success(files)
        }
    }
}
