package com.ikurek.scandroid.core.platform.di

import com.ikurek.scandroid.core.platform.AndroidPlatform
import com.ikurek.scandroid.core.platform.Platform
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface PlatformModule {

    @Binds
    fun bindPlatform(
        androidPlatform: AndroidPlatform
    ): Platform
}
