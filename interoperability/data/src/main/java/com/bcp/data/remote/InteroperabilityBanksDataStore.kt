package com.bcp.data.remote

import com.bcp.data.remote.response.BanksResponse
import kotlinx.coroutines.flow.Flow

interface InteroperabilityBanksDataStore {
    suspend fun getResultBanksList(): Flow<BanksResponse>

}