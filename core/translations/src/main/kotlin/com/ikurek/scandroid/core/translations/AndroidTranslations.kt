package com.ikurek.scandroid.core.translations

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class AndroidTranslations @Inject constructor(
    @ApplicationContext private val context: Context
) : Translations {

    override fun getString(resId: Int) = context.resources.getString(resId)

    override fun getString(resId: Int, vararg formatArgs: Any) =
        context.resources.getString(resId, formatArgs)

    override fun getPlural(resId: Int, quantity: Int) =
        context.resources.getQuantityString(resId, quantity)

    override fun getPlural(resId: Int, quantity: Int, vararg formatArgs: Any) =
        context.resources.getQuantityString(
            resId,
            quantity,
            formatArgs
        )
}
