package com.ikurek.scandroid.core.platform

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider.getUriForFile
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

private const val MimeTypePdf = "application/pdf"
private const val MimeTypeJpeg = "image/jpg"
private const val FileProviderAuthority = "com.ikurek.scandroid.scanfilecontentprovider"

internal class AndroidPlatform @Inject constructor(
    @ApplicationContext private val context: Context
) : Platform {

    override fun openPdfFileOutside(file: File): Result<Unit> = runCatching {
        val pdfFileUri: Uri = getUriForFile(context, FileProviderAuthority, file)

        Intent(Intent.ACTION_VIEW).apply {
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            setDataAndType(pdfFileUri, MimeTypePdf)
        }.let { intent -> context.startActivity(intent) }
    }

    override fun sharePdfFile(file: File): Result<Unit> = runCatching {
        val pdfFileUri: Uri = getUriForFile(context, FileProviderAuthority, file)

        Intent(Intent.ACTION_SEND).apply {
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            putExtra(Intent.EXTRA_STREAM, pdfFileUri)
            setDataAndType(pdfFileUri, MimeTypePdf)
        }.let { intent ->
            Intent.createChooser(intent, null).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }.let { chooserIntent -> context.startActivity(chooserIntent) }
    }

    override fun shareImageFiles(files: List<File>): Result<Unit> = runCatching {
        val imageUris: ArrayList<Uri> = ArrayList(
            files.map { file -> getUriForFile(context, FileProviderAuthority, file) }
        )

        Intent(Intent.ACTION_SEND_MULTIPLE).apply {
            type = MimeTypeJpeg
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris)

            if (imageUris.size == 1) {
                data = imageUris.first()
            }
        }.let { intent ->
            Intent.createChooser(intent, null).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }.let { chooserIntent -> context.startActivity(chooserIntent) }
    }

    override fun openAppInAppStore(): Result<Unit> = runCatching {
        openUrl("market://details?id=${context.packageName}")
    }.recoverCatching {
        openUrl("https://play.google.com/store/apps/details?id=${context.packageName}")
    }

    private fun openUrl(url: String) = Intent(Intent.ACTION_VIEW).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        setData(Uri.parse(url))
    }.let { intent -> context.startActivity(intent) }

    override fun openOssLicenses(): Result<Unit> = runCatching {
        Intent(context, OssLicensesMenuActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }.let { intent -> context.startActivity(intent) }
    }
}
