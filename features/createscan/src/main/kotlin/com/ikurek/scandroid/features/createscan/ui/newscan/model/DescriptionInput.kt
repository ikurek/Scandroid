package com.ikurek.scandroid.features.createscan.ui.newscan.model

internal sealed interface DescriptionInput {
    val value: String

    data class Filled(override val value: String) : DescriptionInput

    data object Empty : DescriptionInput {
        override val value: String = ""
    }
}
