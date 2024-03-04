package com.ikurek.scandroid.feature.home

import androidx.annotation.StringRes
import com.ikurek.scandroid.core.translations.R as TranslationsR

sealed interface HomeInfoDialog {
    @get:StringRes
    val titleRes: Int

    @get:StringRes
    val contentRes: Int

    val isError: Boolean

    data object SdkInitializationFailed : HomeInfoDialog {
        override val titleRes: Int = TranslationsR.string.dialog_error_sdk_initialization_failed_title
        override val contentRes: Int = TranslationsR.string.dialog_error_sdk_initialization_failed_content
        override val isError: Boolean = true
    }

    data object ScannerInitializationFailed : HomeInfoDialog {
        override val titleRes: Int = TranslationsR.string.dialog_error_scanner_initialization_failed_title
        override val contentRes: Int = TranslationsR.string.dialog_error_scanner_initialization_failed_content
        override val isError: Boolean = true
    }

    data object ScannerResultReadFailed : HomeInfoDialog {
        override val titleRes: Int = TranslationsR.string.dialog_error_scanner_initialization_failed_title
        override val contentRes: Int = TranslationsR.string.dialog_error_scanner_initialization_failed_content
        override val isError: Boolean = true
    }
}
