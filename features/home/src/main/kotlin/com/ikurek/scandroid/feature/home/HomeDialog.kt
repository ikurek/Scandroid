package com.ikurek.scandroid.feature.home

import androidx.annotation.StringRes
import com.ikurek.scandroid.core.translations.R as TranslationsR

internal sealed interface HomeDialog {
    @get:StringRes
    val titleRes: Int

    @get:StringRes
    val contentRes: Int

    sealed interface Error : HomeDialog {

        val exception: Throwable

        data class SdkInitializationFailedDialog(
            override val exception: Throwable
        ) : Error {
            override val titleRes: Int =
                TranslationsR.string.dialog_error_sdk_initialization_failed_title
            override val contentRes: Int =
                TranslationsR.string.dialog_error_sdk_initialization_failed_content
        }

        data class ScannerInitializationFailed(
            override val exception: Throwable
        ) : Error {
            override val titleRes: Int =
                TranslationsR.string.dialog_error_scanner_initialization_failed_title
            override val contentRes: Int =
                TranslationsR.string.dialog_error_scanner_initialization_failed_content
        }

        data class ScannerResultReadFailed(
            override val exception: Throwable
        ) : Error {
            override val titleRes: Int =
                TranslationsR.string.dialog_error_scanner_initialization_failed_title
            override val contentRes: Int =
                TranslationsR.string.dialog_error_scanner_initialization_failed_content
        }
    }
}
