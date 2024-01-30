package com.bcp.interop.data.remote

import com.bcp.interop.data.remote.response.BanksResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InteroperabilityBanksDataStoreImpl @Inject constructor() : InteroperabilityBanksDataStore {
    override suspend fun getResultBanksList(): Flow<BanksResponse> {
        TODO("Not yet implemented")
    }


}