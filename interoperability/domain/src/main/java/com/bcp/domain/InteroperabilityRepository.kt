package com.bcp.domain

import com.bcp.domain.model.BankModel
import kotlinx.coroutines.flow.Flow

interface InteroperabilityRepository {

    fun getListOwnBanks(): Flow<List<BankModel>>
    fun getListOtherBanks():Flow<List<BankModel>>

}