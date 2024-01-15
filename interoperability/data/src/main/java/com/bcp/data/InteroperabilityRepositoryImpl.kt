package com.bcp.data

import com.bcp.data.remote.InteroperabilityBanksDataStore
import com.bcp.domain.InteroperabilityRepository
import com.bcp.domain.model.Bank
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InteroperabilityRepositoryImpl @Inject constructor() : InteroperabilityRepository {
    override fun getListOwnBanks(): Flow<List<Bank>> = flow {
        delay(5000L)
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

    override fun getListOtherBanks(): Flow<List<Bank>> = flow {
        delay(500L)
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
}