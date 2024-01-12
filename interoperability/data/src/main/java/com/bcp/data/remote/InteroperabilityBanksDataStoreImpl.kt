package com.bcp.data.remote

import com.bcp.data.remote.response.BanksResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InteroperabilityBanksDataStoreImpl @Inject constructor() : InteroperabilityBanksDataStore {
    override suspend fun getResultBanksList(): Flow<BanksResponse> {
        TODO("Not yet implemented")
    }


}