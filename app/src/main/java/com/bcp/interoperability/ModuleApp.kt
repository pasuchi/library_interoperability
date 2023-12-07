package com.bcp.interoperability

import android.app.Application
import android.content.ContentResolver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ModuleApp {

    @Provides
    fun contentProvider(app: Application): ContentResolver = app.contentResolver


}