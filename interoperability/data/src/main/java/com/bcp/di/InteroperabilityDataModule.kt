package com.bcp.di

import com.bcp.data.InteroperabilityRepositoryImpl
import com.bcp.data.remote.InteroperabilityBanksDataStore
import com.bcp.data.remote.InteroperabilityBanksDataStoreImpl
import com.bcp.domain.InteroperabilityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
interface InteroperabilityDataModule {
    @ViewModelScoped
    @Binds
    abstract fun bindInteroperabilitydataStore(impl: InteroperabilityBanksDataStoreImpl): InteroperabilityBanksDataStore
    @ViewModelScoped
    @Binds
    abstract fun bindInteroperabilityRepository(impl: InteroperabilityRepositoryImpl): InteroperabilityRepository

}