package com.bcp.domain

import com.bcp.domain.model.Bank
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetListBanksUseCase @Inject constructor() {
    operator fun invoke(): Flow<List<Bank>> {
        return flow {
            delay(500L)
            emit(listOf(
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
            ))
        }
    }
}