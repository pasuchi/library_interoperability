package com.bcp.interop.presenter.banklist.modelui

import com.bcp.interop.domain.model.Bank
import com.bcp.presenter.banklist.modelui.BankModel


fun List<Bank>.mapToUiModel(): List<BankModel> = this.map {
    BankModel(it.fullName.orEmpty())
}