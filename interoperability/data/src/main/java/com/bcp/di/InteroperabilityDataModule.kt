package com.bcp.di

import com.bcp.data.InteroperabilityRepositoryImpl
import com.bcp.domain.InteroperabilityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteroperabilityDataModule {

    @Singleton
    @Provides
    fun provideRepo() :InteroperabilityRepository = InteroperabilityRepositoryImpl()


}