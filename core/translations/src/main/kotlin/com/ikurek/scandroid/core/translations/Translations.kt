package com.ikurek.scandroid.core.translations

interface Translations {

    fun getString(resId: Int): String

    fun getString(resId: Int, vararg formatArgs: Any): String

    fun getPlural(resId: Int, quantity: Int): String

    fun getPlural(resId: Int, quantity: Int, vararg formatArgs: Any): String
}
