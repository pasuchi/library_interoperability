package com.bcp.presenter.banklist

import com.bcp.domain.model.Bank

fun BanksIntent.mapToAction(): BanksAction {
    return when (this) {
        is BanksIntent.LoadInit -> BanksAction.GetBanks(phoneNumber)
    }
}

fun List<Bank>.toModel(): List<BankModel>{
    return this.map {
        BankModel(
            name = it.shortName.orEmpty()
        )
    }
}