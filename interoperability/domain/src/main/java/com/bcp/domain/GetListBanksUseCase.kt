package com.bcp.domain

import com.bcp.domain.model.BankModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListBanksUseCase @Inject constructor(val repository: InteroperabilityRepository) {
    operator fun invoke():Flow<List<BankModel>> {
      return  repository.getListOwnBanks()
    }

}