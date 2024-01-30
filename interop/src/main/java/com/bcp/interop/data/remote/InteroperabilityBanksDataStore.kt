package com.bcp.interop.data.remote

import com.bcp.interop.data.remote.response.BanksResponse
import kotlinx.coroutines.flow.Flow

interface InteroperabilityBanksDataStore {
    suspend fun getResultBanksList(): Flow<BanksResponse>

}