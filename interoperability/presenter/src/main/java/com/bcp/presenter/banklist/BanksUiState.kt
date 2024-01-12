package com.bcp.presenter.banklist

sealed class BanksUiState {
    object ShowShimmer : BanksUiState()
    data class RenderBanks(val data: List<BankModel>): BanksUiState()
    data class Error(val message: String) : BanksUiState()
}