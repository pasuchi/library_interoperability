package com.bcp.interop.di

import com.bcp.interop.data.InteroperabilityRepositoryImpl
import com.bcp.interop.domain.InteroperabilityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteroperabilityModule {

    @Singleton
    @Provides
    fun provideRepo() :InteroperabilityRepository = InteroperabilityRepositoryImpl()
}