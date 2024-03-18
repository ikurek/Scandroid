package com.ikurek.scandroid.core.filestore.di

import com.ikurek.scandroid.core.filestore.directoryprovider.DirectoryProvider
import com.ikurek.scandroid.core.filestore.directoryprovider.InternalAppStorageDirectoryProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface FilestoreModule {

    @Binds
    fun bindDirectoryProvider(
        internalAppStorageDirectoryProvider: InternalAppStorageDirectoryProvider
    ): DirectoryProvider
}
