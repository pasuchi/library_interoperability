package com.bcp.interop.domain

import com.bcp.interop.domain.model.Bank
import kotlinx.coroutines.flow.Flow

interface InteroperabilityRepository {

    fun getListOwnBanks(): Flow<List<Bank>>
    fun getListOtherBanks():Flow<List<Bank>>

}