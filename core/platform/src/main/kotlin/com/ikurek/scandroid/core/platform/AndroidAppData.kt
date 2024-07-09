package com.ikurek.scandroid.core.platform

import android.content.Context
import android.content.pm.PackageInfo
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class AndroidAppData @Inject constructor(
    @ApplicationContext private val context: Context
) : AppData {

    private val packageInfo: PackageInfo =
        context.packageManager.getPackageInfo(context.packageName, 0)

    override val versionCode: Long = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        packageInfo.longVersionCode
    } else {
        packageInfo.versionCode.toLong()
    }

    override val versionName: String = packageInfo.versionName

    override val packageIdentifier: String = packageInfo.packageName
}
