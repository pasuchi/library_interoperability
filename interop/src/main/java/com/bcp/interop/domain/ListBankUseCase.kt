package com.bcp.interop.domain

import com.bcp.interop.domain.model.Bank
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListBankUseCase @Inject constructor(private val repo: InteroperabilityRepository) {
    operator fun invoke(): Flow<List<Bank>> {
        return repo.getListOwnBanks()
    }
}