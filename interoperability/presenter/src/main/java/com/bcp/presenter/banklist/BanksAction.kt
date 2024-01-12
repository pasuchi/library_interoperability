package com.bcp.presenter.banklist

sealed class BanksAction {
    data class GetBanks(val phoneNumber: String) : BanksAction()
}