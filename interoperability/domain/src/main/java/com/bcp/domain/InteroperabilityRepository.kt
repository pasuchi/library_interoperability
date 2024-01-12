package com.bcp.domain

import com.bcp.domain.model.Bank
import kotlinx.coroutines.flow.Flow

interface InteroperabilityRepository {

    fun getListOwnBanks(): Flow<List<Bank>>
    fun getListOtherBanks():Flow<List<Bank>>

}