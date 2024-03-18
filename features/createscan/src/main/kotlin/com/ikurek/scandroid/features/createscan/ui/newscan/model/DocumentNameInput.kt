package com.ikurek.scandroid.features.createscan.ui.newscan.model

internal sealed interface DocumentNameInput {
    val value: String

    data class Filled(override val value: String) : DocumentNameInput

    data object Empty : DocumentNameInput {
        override val value: String = ""
    }
}
