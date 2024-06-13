package com.ikurek.scandroid.core.translations.di

import com.ikurek.scandroid.core.translations.AndroidTranslations
import com.ikurek.scandroid.core.translations.Translations
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface TranslationsModule {

    @Binds
    fun bindsTranslations(impl: AndroidTranslations): Translations
}
