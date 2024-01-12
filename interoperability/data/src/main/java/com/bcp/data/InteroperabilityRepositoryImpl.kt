package com.bcp.data

import com.bcp.domain.InteroperabilityRepository
import com.bcp.domain.model.Bank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InteroperabilityRepositoryImpl @Inject constructor(): InteroperabilityRepository {
    override fun getListOwnBanks(): Flow<List<Bank>>  = flow {
        emit(
            listOf(
                Bank(
                    financialEntityId = "901",
                    formattedFinancialEntityId = "901",
                    shortName = "YAPE",
                    fullName = "YAPE",
                    icon = "lg_yape_dark"
                ),
                Bank(
                    financialEntityId = "902",
                    formattedFinancialEntityId = "902",
                    shortName = "BCP",
                    fullName = "BCP",
                    icon = "lg_yape_dark"
                )
            )
        )
    }

    override fun getListOtherBanks(): Flow<List<Bank>> {
        TODO("Not yet implemented")
    }
}