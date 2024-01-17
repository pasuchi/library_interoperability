package com.bcp.presenter.banklist

import com.bcp.presenter.banklist.modelui.BankModel

data class BanksUiState(
    val isLoaderShimmer: Boolean = false,
    val listBank: List<BankModel> = emptyList()
)