package com.ikurek.scandroid.features.createscan.ui.newscan

import androidx.annotation.StringRes
import com.ikurek.scandroid.core.translations.R as TranslationsR

internal sealed interface NewScanDialog {
    @get:StringRes
    val titleRes: Int

    @get:StringRes
    val contentRes: Int

    sealed interface Error : NewScanDialog {

        val exception: Throwable

        data class ScanSaveFailed(
            override val exception: Throwable
        ) : Error {
            override val titleRes: Int =
                TranslationsR.string.dialog_error_sdk_initialization_failed_title
            override val contentRes: Int =
                TranslationsR.string.dialog_error_sdk_initialization_failed_content
        }
    }
}
