package com.bcp.presenter.banklist.modelui

import com.bcp.domain.model.Bank

fun List<Bank>.mapToUiModel(): List<BankModel> = this.map {
    BankModel(it.fullName.orEmpty())
}