package com.bcp.domain

import com.bcp.domain.model.Bank
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetListBanksUseCase @Inject constructor(private val repo: InteroperabilityRepository) {
    operator fun invoke(): Flow<List<Bank>> {
        return repo.getListOwnBanks()
    }
}