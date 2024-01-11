package com.bcp.data

import com.bcp.domain.InteroperabilityRepository
import com.bcp.domain.model.BankModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InteroperabilityRepositoryImpl @Inject constructor(): InteroperabilityRepository {
    override fun getListOwnBanks(): Flow<List<BankModel>>  = flow {

    }
    override fun getListOtherBanks(): Flow<List<BankModel>> {
        TODO("Not yet implemented")
    }


}