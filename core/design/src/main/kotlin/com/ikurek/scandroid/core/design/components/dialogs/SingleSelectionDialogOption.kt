package com.ikurek.scandroid.core.design.components.dialogs

data class SingleSelectionDialogOption<T>(
    val value: T,
    val text: String,
    val description: String? = null
)
